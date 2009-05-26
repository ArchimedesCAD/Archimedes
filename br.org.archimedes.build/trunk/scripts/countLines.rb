#!/usr/bin/ruby

require 'rubygems'
require 'header-inserter/project'

def count_line(filepath)
    file = File.new filepath, "r"
    file.inject(0){ |sum, line| sum + (/^\s*$/.match(line) ? 0 : 1) }
end

def print_stats(projects, file_type, data_name)
    line_count = 0
    file_count = 0
    line_max = 0
    line_min = 99999999999
    projects.each do |project|
        files = project.list(file_type).reject{|file| (not File.exist?(file.absolute_path)) or File.directory?(file.absolute_path) }
        file_count += files.size
        line_count += files.inject(0){|lines, file| lines + count_line(file.absolute_path) }
        line_max = files.inject(line_max){|old_max, file|  max = old_max; count = count_line(file.absolute_path); max = count if count > max; max }
        line_min = files.inject(line_min){|old_min, file|  min = old_min; count = count_line(file.absolute_path); min = count if count < min; min }
    end
    puts "Counted #{file_count} #{data_name} resulting in #{line_count} lines (mean is #{line_count/file_count} lines per files - min size is #{line_min} and max is #{line_max})."
end

dir = ARGV.first
dir ||= "."

main_project = Project.new dir
subprojects_files = main_project.list(/^br.org.archimedes[^\/]*$/)

subprojects = subprojects_files.map{ |project_file| Project.new(project_file.absolute_path) }
tests = subprojects.select{|p| /.*test(?:s)?$/.match p.path}
code = subprojects - tests

puts "#{subprojects.size} subprojects created for archimedes: #{code.size} for code and #{tests.size} for tests."
puts ""
print_stats(code, :java, "java files")
puts ""
print_stats(tests, :java, "java test files")
puts ""
print_stats(subprojects, :xml, "XML files")
