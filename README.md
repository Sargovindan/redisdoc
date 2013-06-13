<h1>redisdoc = Redis documentation console</h1>

Why this project exists? Because there is currently no such tool and I needed to document my Redis database.
So I created a console that mimics Redis console in it's functionality, but contains specific commands to document redis database.

<h2>How to download:</h2> 
See <code>bin</code> directory, which contains:
<ul>
	<li><code>redisdoc.jar</code>: Java executable file</li>
	<li><code>redisdoc.properties</code>: configuration</li>
</ul>

<h2>How to run:</h2>
<code>java -jar redisdoc.jar</code>

<h2>Functionality:</h2>

<h3>Available commands:</h3>
<code>help</code> show help for all commands

<code>help &lt;command&gt;</code> show help for specific command

<code>quit OR exit</code> quit application

<code>select &lt;number&gt;</code> select redis database with your keys

<code>dselect &lt;number&gt;</code> select redis database for documentation

<code>keys &lt;pattern&gt;</code> select keys using pattern

<code>dset &lt;doc-name (pattern)&gt; &lt;description&gt;</code> set documentation key (pattern) with description

<code>ddel &lt;doc-name&gt;</code> deletes documentation key



<code>dget &lt;doc-name&gt;</code>
documentation key detail. Contains several optional parameters, that can be combined

<code>dget --type &lt;doc-name&gt;</code>
show keys with their types

<code>dget --detail &lt;doc-name&gt;</code>
show keys with their values or length

<code>dget --ttl &lt;doc-name&gt;</code>
show keys with ttl

<code>dkeys &lt;pattern&gt;</code> list documentation keys using pattern



<code>dundkeys</code>
retrieves undocumented keys from your database. Contains several optional parameters, that can be combined

<code>dundkeys --type</code>
show undocumented keys with their types

<code>dundkeys --detail</code>
show undocumented keys with their values or length

<code>dundkeys --ttl</code>
show undocumented keys with ttl

