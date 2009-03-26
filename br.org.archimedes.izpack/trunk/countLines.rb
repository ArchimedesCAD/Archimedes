#!/usr/bin/ruby

require 'find'

dir = ARGV.first
dir ||= "."

def find_subdirs_matching(starting_dir, regexp)
  dirs = []
  Find.find(starting_dir) do |path|
    if regexp.match path
      dirs << path
    end
    if path != "."
      Find.prune
    end
  end
  return dirs
end

def find_files_within_matching(starting_dir, regexp)
  files = []
  Find.find(starting_dir) do |path|
    if (not (File.directory? path)) and (regexp.match path)
      files << path
    end
  end
  return files
end

def count_lines_not_matching(file, regexp)
  lines = []
  File.new(file, "r").each do |line|
    lines << line if not (regexp.match line)
  end
  return lines
end

def collect_stats(projects, file_regexp, files_type, line_regexp, lines_type)
  file_count = 0
  line_count = 0
  projects.each do |project|
    files = find_files_within_matching(project, file_regexp)
#    puts "\t#{files.size} #{files_type} within #{project}" if files.size > 0

    files.each do |file|
      lines = count_lines_not_matching(file, line_regexp)
#      puts "\t\t#{lines.size} #{lines_type} for #{file}" if lines.size > 0
      line_count += lines.size
    end
    file_count += files.size
  end
  puts "#{file_count} #{files_type} for archimedes." if file_count > 0
  puts "#{line_count} #{lines_type} for archimedes." if line_count > 0
end

projects = find_subdirs_matching(dir, /^#{dir}\/br\.org\.archimedes.*/)
tests = projects.select{|p| /.*tests$/.match p}
code = projects - tests

puts "#{projects.size} projects created for archimedes: #{code.size} for code and #{tests.size} for tests."
puts ""
collect_stats(code, /.*\.java$/, "java files", /^\w*$/, "LoC")
puts ""
collect_stats(tests, /.*\.java$/, "java test files", /^\w*$/, "test LoC")
puts ""
collect_stats(projects, /.*\.xml$/, "XML files", /^\w*$/, "XML LoC")
