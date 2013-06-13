<h1>redisdoc = Redis documentation console</h1>

Why this project exists? Because there is currently no such tool and I wanted to document my Redis database.
So I created a console that mimics Redis console.

<h2>How to download:</h2> 
See bin directory, which contains:
	- redisdoc.jar: Java executable file
	- redisdoc.properties: configuration

<h2>How to run:</h2>
<code>java -jar redisdoc.jar</code>

<h2>Functionality:</h2>

<h3>Available commands:</h3>
<code>help</code>
show help for all commands

<code>help &lt;command&gt;</code>
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

