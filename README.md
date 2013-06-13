redisdoc = Redis documentation console

Why this project exists? Because there is currently no such tool and I wanted to document my Redis database.
So I created a console that mimics Redis console.

How to download: See bin directory, which contains:
	- redisdoc.jar: Java executable file
	- redisdoc.properties: configuration

How to run:
<code>java -jar redisdoc.jar</code>

Functionality:

Available commands:
help
show help for all commands
help <command>
show help for specific command

quit OR exit
quit application

select <number>
select redis database with your keys

dselect <number>
select redis database for documentation

keys <pattern>
select keys using pattern

dset <doc-name (pattern)> <description>
set documentation key (pattern) with description

ddel <doc-name>
deletes documentation key

dget <doc-name>
documentation key detail
contains several optional parameters, that can be combined
dget --type <doc-name>
show keys with their types
dget --detail <doc-name>
show keys with their values or length
dget --ttl <doc-name>
show keys with ttl

dkeys <pattern>
list documentation keys using pattern

dundkeys
retrieves undocumented keys from your database
contains several optional parameters, that can be combined
dundkeys --type
show undocumented keys with their types
dundkeys --detail
show undocumented keys with their values or length
dundkeys --ttl
show undocumented keys with ttl

