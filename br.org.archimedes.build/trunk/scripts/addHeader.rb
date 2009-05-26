#!/usr/bin/ruby

require 'rubygems'
require 'header-inserter/project'

dir = ARGV.first
dir ||= "."

header = """/**
 * Copyright (c) REPLACE_CREATE_AND_CURRENT_YEAR Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * REPLACE_CREATOR - initial API and implementation<br>
REPLACE_CONTRIBUTORS * <br>
 * This file was created on REPLACE_DATE, REPLACE_TIME, by REPLACE_CREATOR.<br>
 * It is part of package REPLACE_PACKAGE on the REPLACE_PROJECT project.<br>
REPLACE_REST */
"""

@authors = { "ceci" => "Cecilia Fernandes",
             "cmsato" => "Cristiane M. Sato",
             "cesarse" => "César Seragiotto",
             "dahora" => "Bruno V. da Hora",
             "entaofalou" => "Helton Rosa",
             "hugo.corbucci" => "Hugo Corbucci",
             "jrenaut" => "Julien Renaut",
             "keizo" => "Jonas K. Hirata",
             "lreal" => "Luiz C. Real",
             "nitao" => "Hugo Corbucci",
             "night" => "Hugo Corbucci",
             "marivb" => "Mariana V. Bravo",
             "mpmoreti" => "Marcos P. Moreti",
             "pablolh" => "Paulo L. Huaman",
             "rsider" => "Ricardo Sider",
             "ruivo" => "Eduardo O. de Souza",
             "victordlopes" => "Victor D. Lopes",
             "wrp" => "Wellington R. Pinheiro" }

def generate_year_range file
  created_year = file.created_on.year;
	current_year = Date.today.year
	if created_year == current_year
	 	"#{current_year}"
	else
		"#{created_year}, #{current_year}" 
	end
end

def extract_clean_comment comment
  comment.gsub(/\n\s*\*\s+/, " ").gsub(/\/\*{1,2}/, "").gsub(/\*\//, "").strip
end

def recover_header_data file
  old_comment = extract_clean_comment file.original_header
  if /(?:This file was c|C)reated on \d{2,4}\/\d{2}\/\d{2,4}, \d{2}:\d{2}:\d{2}, by \w+\. It is part of [\w\.]+ on the [\w\.]+ project\.(.*)/.match old_comment
    old_comment = $1
  elsif /Created on \d{2}\/\d{2}\/\d{4}(.*)/.match old_comment
    old_comment = $1
  end
  return "" if /^\s*$/.match(old_comment)
  " * #{old_comment}\n"
end

def contributors_names file
  file.contributors().map{|contributor|
                  value = contributor
                  (value = @authors[contributor]) if @authors.has_key?(contributor)
                  puts "Couldn't find contributor '#{contributor}' in the list of authors. Class of contributor is #{contributor.class}" unless @authors.has_key?(contributor)
                  value
                  }.uniq
end

def generate_contributors file
  contributors = contributors_names(file)
  if contributors.size <= 1
    ""
  else
    " * " + contributors[1..contributors.size].join(", ") + " - later contributions<br>\n"
  end
end

def recover_creator file
  old_comment = extract_clean_comment file.original_header
  return @authors[$1] if /.* by (\w+).*/.match old_comment
  contributors_names(file)[0]
end

def recover_creation_date file
  old_comment = extract_clean_comment file.original_header
  if /.* on ((?:\d{4}\/\d{2}\/\d{2})|(?:\d{2}\/\d{2}\/\d{4})|(?:\w+ \d{1,2}, \d{4}))(?:, (\d{2}:\d{2}:\d{2})).*/.match old_comment
    date = $1
    time = $2
  end

  (date = "#{$3}/#{$2}/#{$1}") if /(\d{2})\/(\d{2})\/(\d{4})/.match(date)
  
  (time = file.created_on.strftime("%H:%M:%S")) if time.nil?
  (date = file.created_on.strftime("%Y/%m/%d")) if date.nil?
    
  return DateTime.strptime(date + " " + time, "%Y/%m/%d %H:%M:%S") if /\d{4}\/\d{2}\/\d{2}/.match(date)
  return DateTime.strptime(date + " " + time, "%b %d, %Y %H:%M:%S") if /\w+ \d{1,2}, \d{4}/.match(date)
  
  DateTime.now
end

project = Project.new dir
subprojects = project.list(/^br\.org\.archimedes[^\/]*$/).reverse
hooks = { "REPLACE_CREATE_AND_CURRENT_YEAR" => method(:generate_year_range),
		  "REPLACE_CREATOR" => method(:recover_creator),
      "REPLACE_CONTRIBUTORS" => method(:generate_contributors),
		  "REPLACE_DATE" => lambda { |file| recover_creation_date(file).strftime("%Y/%m/%d") },
		  "REPLACE_TIME" => lambda { |file| recover_creation_date(file).strftime("%H:%M:%S") },
		  "REPLACE_PACKAGE" => lambda { |file| /#{dir}\/br.org.archimedes.*\/(br\/org\/archimedes.*)\/.*.java/.match file.absolute_path;$1.gsub("/", ".") },
		  "REPLACE_PROJECT" => lambda { |file| /#{dir}\/(br.org.archimedes[^\/]*)\/.*.java/.match file.absolute_path;$1 },
		  "REPLACE_REST" => method(:recover_header_data)
	   }
total_files = 0
subprojects.each do |project_dir|
  project_path = project_dir.path
  project = Project.new "#{dir}/#{[project_path]}"
  files = project.list(/.*\/br\/org\/archimedes.*\.java$/)
  total_files += files.size
  files.each do |file|
    old_header = file.original_header
    new_header = file.generate_header(header, hooks)
    file.add_header new_header, old_header
  end
end

puts "Generated a new header for #{total_files} files on #{subprojects.size} projects."
