#!/usr/bin/ruby

require 'rubygems'
require 'header-inserter/project'

def cat(filepath)
    file = File.new filepath, "r"
    file.inject(""){ |sum, line| sum + line }
end

def concatenate(projects, file_type, result_path)
    text = ""
    projects.each do |project|
        files = project.list(file_type).reject{|file| (not File.exist?(file.absolute_path)) or File.directory?(file.absolute_path) }
        text += files.inject(""){|text, file| text + cat(file.absolute_path) }
    end
    file = File.new result_path, "a"
    file.puts text
    file.close
end

dir = ARGV.first
dir ||= "."

main_project = Project.new dir
subprojects_files = main_project.list(/^br.org.archimedes[^\/]*$/)

subprojects = subprojects_files.map{ |project_file| Project.new(project_file.absolute_path) }
tests = subprojects.select{|p| /.*test(?:s)?$/.match p.path}
code = subprojects - tests

concatenate(code, :java, "/tmp/javas.txt")
concatenate(tests, :java, "/tmp/tests.txt")
concatenate(subprojects, :xml, "/tmp/xmls.txt")
concatenate(subprojects, /^.*\.(java|xml)$/, "/tmp/all.txt")
