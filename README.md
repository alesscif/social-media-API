<h2 id="overview">Overview</h2>

<p>For this assessment, you are tasked with implementing a RESTful API using Spring Boot, JPA, and Postgresql. Specifically, you will be implementing an API that exposes operations for social media data that resembles the conceptual model of Twitter.</p>

<p>You will implement this API from scratch, working from a series of endpoint specifications (found at the end of this document) to develop a mental model of the data. You will develop a suitable database schema and write Spring services and controllers to handle requests, perform validation and business logic, and to transform data between the API and database models.</p>

<h2 id="testing-the-api">Testing the API</h2>
<p>Included in this skeleton are 2 json files required to run the test suite for this final project. To run the tests you will need postman’s newman CLI. To install newman run the command <code class="language-plaintext highlighter-rouge">npm install -g newman</code>. Once newman is installed you need to navigate to the folder containing the Assessment 1 Test Suite &amp; Assessment 1 environment json files. Once there you can run the command <code class="language-plaintext highlighter-rouge">newman run "Assessment 1 Test Suite.postman_collection.json" -e "Assessment 1.postman_environment.json"</code>. When all tests are passing successfully you will pass 330 assertions and should see something similar to the following in your terminal:</p>

<p><img width="458" alt="successful_tests" src="https://user-images.githubusercontent.com/12191780/222555974-53992ad3-155c-4e77-9205-bc3b908e093c.png"></p>

<h2 id="reading-these-requirements">Reading these Requirements</h2>

<h3 id="restful-endpoint-methods-and-urls">RESTful Endpoint Methods and URLs</h3>
<p>Each endpoint you are required to implement is documented by the REST method and URL used to access it. For example, an endpoint used to access the list of dogs maintained by a server might be described like:</p>

<p><code class="language-plaintext highlighter-rouge">GET  dogs</code></p>

<p>This tells us that the endpoint requires the <code class="language-plaintext highlighter-rouge">GET</code> HTTP method and is located at the <code class="language-plaintext highlighter-rouge">dogs</code> url, i.e. at <code class="language-plaintext highlighter-rouge">http://host:port/dogs</code>.</p>

<h4 id="url-variables">URL Variables</h4>
<p>Some endpoints have variables in their urls, and these are represented by a variable name surrounded by curly braces. For example, an endpoint that returns a breed of dog by name might be described by the following syntax:</p>

<p><code class="language-plaintext highlighter-rouge">GET breeds/{breedName}</code></p>

<p>This tells us that the endpoint captures the path segment following <code class="language-plaintext highlighter-rouge">breeds/</code> with the variable <code class="language-plaintext highlighter-rouge">breedName</code>.</p>

<p>Remember that the curly braces themselves are not part of the url, but anything outside of them is.</p>

<h4 id="trailing-slashes">Trailing Slashes</h4>
<p>The endpoint specifications never supply a trailing slash, but they are allowed. It is up to you to decide whether you prefer trailing slashes for API endpoint URLs or not, but whichever you choose, be consistent from endpoint to endpoint.</p>

<h3 id="types-and-object-properties">Types and Object Properties</h3>
<p>The syntax used to describe the request and response bodies for each required api endpoint is a variation of javascript’s object literal syntax, in order to promote legibility, but the endpoints themselves should use JSON to represent data.</p>

<p>Object literals are used to describe the shape of each data model, and property values are used to describe the property’s data type. For example, a <code class="language-plaintext highlighter-rouge">Dog</code> data type might be described by the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Dog</span>
  <span class="nl">name</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">age</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>
<p>This tells us that a dog has two properties, <code class="language-plaintext highlighter-rouge">name</code> and <code class="language-plaintext highlighter-rouge">age</code>, and that they should be a <code class="language-plaintext highlighter-rouge">string</code> and <code class="language-plaintext highlighter-rouge">integer</code>, respectively.</p>

<h4 id="optional-properties">Optional properties</h4>
<p>Some properties are optional, meaning that they can be represented by <code class="language-plaintext highlighter-rouge">undefined</code> in javascript or <code class="language-plaintext highlighter-rouge">null</code> in java or sql. This is represented by giving a <code class="language-plaintext highlighter-rouge">?</code> suffix to the property name. For example, a <code class="language-plaintext highlighter-rouge">Dog</code> type like the one defined before could have an optional nickname, which might be described by the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Dog</span>
  <span class="nl">name</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">nickname</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">age</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>
<p>This tells us that a <code class="language-plaintext highlighter-rouge">Dog</code> has a property <code class="language-plaintext highlighter-rouge">nickname</code> that may be ‘string’ or may not be present at all.</p>

<p>Any properties without a <code class="language-plaintext highlighter-rouge">?</code> suffix should be considered required.</p>

<p>Keep in mind this is not valid javascript or JSON syntax, and that the <code class="language-plaintext highlighter-rouge">?</code> is not part of the property name.</p>

<h4 id="built-in-types">Built-in Types</h4>
<p>Some types, like <code class="language-plaintext highlighter-rouge">'string'</code> and ‘<code class="language-plaintext highlighter-rouge">integer</code>’, mean exactly what you would expect them to - they refer to simple types common to both Java and JSON. Others, though, are less obvious, and some require different representations in Java, JSON, and SQL.</p>

<p>To ensure consistency, here is a quick overview of some of the common types used in this specification.</p>

<ul>
  <li><code class="language-plaintext highlighter-rouge">'string'</code> refers to a string of unicode characters, and can be represented by the <code class="language-plaintext highlighter-rouge">String</code> types in all relevant languages</li>
  <li><code class="language-plaintext highlighter-rouge">'integer'</code> refers to a 32-bit signed integer, and can be represented by the <code class="language-plaintext highlighter-rouge">number</code> type in JSON and the <code class="language-plaintext highlighter-rouge">Integer</code> type in Java.</li>
  <li><code class="language-plaintext highlighter-rouge">'timestamp'</code> refers to a UNIX timestamp, i.e. the number of milliseconds since the beginning of the UNIX epoch - January 1, 1970. In JSON, this should be represented as a number, specifically a <code class="language-plaintext highlighter-rouge">long</code>. On the server side, as well as in the database, this should be represented as an instance of <code class="language-plaintext highlighter-rouge">java.sql.Timestamp</code>.</li>
</ul>

<h4 id="custom-types">Custom Types</h4>
<p>Property types can also refer to types defined in this specification. For example, an owner for the <code class="language-plaintext highlighter-rouge">Dog</code> type defined above might be described by the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Owner</span>
  <span class="nl">dog</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Dog</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>
<p>This tells us that an <code class="language-plaintext highlighter-rouge">Owner</code> has a property <code class="language-plaintext highlighter-rouge">dog</code> that is described by the <code class="language-plaintext highlighter-rouge">Dog</code> type, defined elsewhere in the specification.</p>

<h4 id="anonymous-types">Anonymous Types</h4>
<p>Sometimes a type is never reused in the specification. In those cases, an object literal can be used to describe the type without naming it. For example, a <code class="language-plaintext highlighter-rouge">ChewToy</code> data type might be described by the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// ChewToy</span>
  <span class="nl">material</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">color</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">dimensions</span><span class="p">:</span> <span class="p">{</span>
    <span class="nl">width</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span><span class="p">,</span>
    <span class="nx">height</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span>
  <span class="p">}</span>
<span class="p">}</span>
</code></pre></div></div>
<p>Here we could have defined a <code class="language-plaintext highlighter-rouge">Dimensions</code> type with the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Dimensions</span>
  <span class="nl">width</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">height</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>
<p>But if <code class="language-plaintext highlighter-rouge">ChewToy</code> is the only type that makes use of <code class="language-plaintext highlighter-rouge">Dimensions</code>, it’s easier to define <code class="language-plaintext highlighter-rouge">Dimensions</code> as an anonymous type.</p>

<h4 id="array-types">Array Types</h4>
<p>If a property should be an array of a specific type of element, it is represented as an array literal with the element type as a string inside the array. For example, a kennel with a list of dogs might be described by the following syntax:</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Kennel</span>
  <span class="nl">dogs</span><span class="p">:</span> <span class="p">[</span><span class="dl">'</span><span class="s1">Dog</span><span class="dl">'</span><span class="p">]</span>
<span class="p">}</span>
</code></pre></div></div>
<p>This tells us that a <code class="language-plaintext highlighter-rouge">Kennel</code> has a property <code class="language-plaintext highlighter-rouge">dogs</code> that is an array of elements, the type of each of which is described by the <code class="language-plaintext highlighter-rouge">Dog</code> type</p>

<h2 id="entity-relationship-diagram">Entity Relationship Diagram</h2>
<p><img src="https://user-images.githubusercontent.com/12191780/187276918-ccb2d373-be3b-42ff-a74d-5560ba806a10.png" alt="Spring Assessment ERD"></p>

<p>This ERD represents the database that students will create for this project. Students should only create three classes, <code class="language-plaintext highlighter-rouge">User</code>, <code class="language-plaintext highlighter-rouge">Tweet</code>, and <code class="language-plaintext highlighter-rouge">Hashtag</code>, annotated with <code class="language-plaintext highlighter-rouge">@Entity</code>. There are, however, two additional classes that students will need to create for this project: <code class="language-plaintext highlighter-rouge">Credentials</code> and <code class="language-plaintext highlighter-rouge">Profile</code>. These two classes will be annotated with <code class="language-plaintext highlighter-rouge">@Embeddable</code> and will be used inside of the <code class="language-plaintext highlighter-rouge">User</code> entity class with the <code class="language-plaintext highlighter-rouge">@Embedded</code> annotation. This allows us to maintain credentials and profile as seperate objects in Java while still being stored in just one table in the database.</p>

<p><strong>IMPORTANT:</strong> The <code class="language-plaintext highlighter-rouge">User</code> entity will also need to use an <code class="language-plaintext highlighter-rouge">@Table(name=&lt;newName&gt;)</code> annotation to give its table a different name as <code class="language-plaintext highlighter-rouge">user</code> is a reserved keyword in PostgreSQL.</p>

<h2 id="api-data-types">API Data Types</h2>
<p>The semantics of the operations exposed by the API endpoints themselves are discussed in the following section, but in this section, the API data model is defined and the conceptual model for the application is explained in some depth. Additionally, some hints and constraints for the database model are discussed here.</p>

<p>In general, the data types defined here are in their general, read-only forms. That means that these are the versions of the models that are returned by <code class="language-plaintext highlighter-rouge">GET</code> operations or nested inside other objects as auxiliary data. Most <code class="language-plaintext highlighter-rouge">POST</code> operations, which often create new records in the database, require specialized versions of these models. Those special cases are covered by the endpoint specifications themselves unless otherwise noted.</p>

<h3 id="user">User</h3>
<p>A user of the application. The <code class="language-plaintext highlighter-rouge">username</code> must be unique. The <code class="language-plaintext highlighter-rouge">joined</code> timestamp should be assigned when the user is first created, and must never be updated.</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// User</span>
  <span class="nl">username</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">profile</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Profile</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">joined</span><span class="p">:</span> <span class="dl">'</span><span class="s1">timestamp</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h3 id="profile">Profile</h3>
<p>A user’s profile information. Only the <code class="language-plaintext highlighter-rouge">email</code> property is required.</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Profile</span>
  <span class="nx">firstName</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">lastName</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">email</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">phone</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h3 id="credentials">Credentials</h3>
<p>A user’s credentials. These are mostly used for validation and authentication during operations specific to a user. Passwords are plain text for the sake of academic simplicity, and it should be kept in mind that this is never appropriate in the real world.</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Credentials</span>
  <span class="nl">username</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">password</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h3 id="hashtag">Hashtag</h3>
<p>A hashtag associated with tweets that contain its label. The <code class="language-plaintext highlighter-rouge">label</code> property must be unique, but is case-insensitive. The <code class="language-plaintext highlighter-rouge">firstUsed</code> timestamp should be assigned on creation, and must never be updated. The <code class="language-plaintext highlighter-rouge">lastUsed</code> timestamp should be updated every time a new tweet is tagged with the hashtag.</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Hashtag</span>
  <span class="nl">label</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">firstUsed</span><span class="p">:</span> <span class="dl">'</span><span class="s1">timestamp</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">lastUsed</span><span class="p">:</span> <span class="dl">'</span><span class="s1">timestamp</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h2 id="tweet">Tweet</h2>
<p>A tweet posted by a user. The <code class="language-plaintext highlighter-rouge">posted</code> timestamp should be assigned when the tweet is first created, and must not be updated.</p>

<p>There are three distinct variations of tweets: simple, repost, and reply.</p>
<ul>
  <li>A simple tweet has a <code class="language-plaintext highlighter-rouge">content</code> value but no <code class="language-plaintext highlighter-rouge">inReplyTo</code> or <code class="language-plaintext highlighter-rouge">repostOf</code> values</li>
  <li>A repost has a <code class="language-plaintext highlighter-rouge">repostOf</code> value but no <code class="language-plaintext highlighter-rouge">content</code> or <code class="language-plaintext highlighter-rouge">inReplyTo</code> values</li>
  <li>A reply has <code class="language-plaintext highlighter-rouge">content</code> and <code class="language-plaintext highlighter-rouge">inReplyTo</code> values, but no <code class="language-plaintext highlighter-rouge">repostOf</code> value</li>
</ul>

<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Tweet</span>
  <span class="nl">id</span><span class="p">:</span> <span class="dl">'</span><span class="s1">integer</span><span class="dl">'</span>
  <span class="nx">author</span><span class="p">:</span> <span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">posted</span><span class="p">:</span> <span class="dl">'</span><span class="s1">timestamp</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">content</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">inReplyTo</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">repostOf</span><span class="p">?:</span> <span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h3 id="context">Context</h3>
<p>The reply context of a tweet. The <code class="language-plaintext highlighter-rouge">before</code> property represents the chain of replies that led to the <code class="language-plaintext highlighter-rouge">target</code> tweet, and the <code class="language-plaintext highlighter-rouge">after</code> property represents the chain of replies that followed the <code class="language-plaintext highlighter-rouge">target</code> tweet.</p>

<p>The chains should be in chronological order, and the <code class="language-plaintext highlighter-rouge">after</code> chain should include all replies of replies, meaning that all branches of replies must be flattened into a single chronological list to fully satisfy the requirements.</p>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span> <span class="c1">// Context</span>
  <span class="nl">target</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">before</span><span class="p">:</span> <span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">],</span>
  <span class="nx">after</span><span class="p">:</span> <span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
<span class="p">}</span>
</code></pre></div></div>

<h2 id="api-endpoints">API Endpoints</h2>

<h3 id="get---validatetagexistslabel"><code class="language-plaintext highlighter-rouge">GET   validate/tag/exists/{label}</code></h3>
<p>Checks whether or not a given hashtag exists.</p>

<h4 id="response">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">boolean</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get---validateusernameexistsusername"><code class="language-plaintext highlighter-rouge">GET   validate/username/exists/@{username}</code></h3>
<p>Checks whether or not a given username exists.</p>

<h4 id="response-1">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">boolean</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get---validateusernameavailableusername"><code class="language-plaintext highlighter-rouge">GET   validate/username/available/@{username}</code></h3>
<p>Checks whether or not a given username is available.</p>

<h4 id="response-2">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">boolean</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----users"><code class="language-plaintext highlighter-rouge">GET     users</code></h3>
<p>Retrieves all active (non-deleted) users as an array.</p>

<h4 id="response-3">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="post----users"><code class="language-plaintext highlighter-rouge">POST    users</code></h3>
<p>Creates a new user. If any required fields are missing or the <code class="language-plaintext highlighter-rouge">username</code> provided is already taken, an error should be sent in lieu of a response.</p>

<p>If the given credentials match a previously-deleted user, re-activate the deleted user instead of creating a new one.</p>

<h4 id="request">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span>
  <span class="nl">credentials</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">profile</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Profile</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h4 id="response-4">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----usersusername"><code class="language-plaintext highlighter-rouge">GET     users/@{username}</code></h3>
<p>Retrieves a user with the given username. If no such user exists or is deleted, an error should be sent in lieu of a response.</p>

<h4 id="response-5">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="patch---usersusername"><code class="language-plaintext highlighter-rouge">PATCH   users/@{username}</code></h3>
<p>Updates the profile of a user with the given username. If no such user exists, the user is deleted, or the provided credentials do not match the user, an error should be sent in lieu of a response. In the case of a successful update, the returned user should contain the updated data.</p>

<h4 id="request-1">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span>
  <span class="nl">credentials</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">profile</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Profile</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h4 id="response-6">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="delete--usersusername"><code class="language-plaintext highlighter-rouge">DELETE  users/@{username}</code></h3>
<p>“Deletes” a user with the given username. If no such user exists or the provided credentials do not match the user, an error should be sent in lieu of a response. If a user is successfully “deleted”, the response should contain the user data prior to deletion.</p>

<p><strong>IMPORTANT:</strong> This action should not actually drop any records from the database! Instead, develop a way to keep track of “deleted” users so that if a user is re-activated, all of their tweets and information are restored.</p>

<h4 id="request-2">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h4 id="response-7">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="post----usersusernamefollow"><code class="language-plaintext highlighter-rouge">POST    users/@{username}/follow</code></h3>
<p>Subscribes the user whose credentials are provided by the request body to the user whose username is given in the url. If there is already a following relationship between the two users, no such followable user exists (deleted or never created), or the credentials provided do not match an active user in the database, an error should be sent as a response. If successful, no data is sent.</p>

<h4 id="request-3">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="post----usersusernameunfollow"><code class="language-plaintext highlighter-rouge">POST    users/@{username}/unfollow</code></h3>
<p>Unsubscribes the user whose credentials are provided by the request body from the user whose username is given in the url. If there is no preexisting following relationship between the two users, no such followable user exists (deleted or never created), or the credentials provided do not match an active user in the database, an error should be sent as a response. If successful, no data is sent.</p>

<h4 id="request-4">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----usersusernamefeed"><code class="language-plaintext highlighter-rouge">GET     users/@{username}/feed</code></h3>
<p>Retrieves all (non-deleted) tweets authored by the user with the given username, as well as all (non-deleted) tweets authored by users the given user is following. This includes simple tweets, reposts, and replies. The tweets should appear in reverse-chronological order. If no active user with that username exists (deleted or never created), an error should be sent in lieu of a response.</p>

<h4 id="response-8">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----usersusernametweets"><code class="language-plaintext highlighter-rouge">GET     users/@{username}/tweets</code></h3>
<p>Retrieves all (non-deleted) tweets authored by the user with the given username. This includes simple tweets, reposts, and replies. The tweets should appear in reverse-chronological order. If no active user with that username exists (deleted or never created), an error should be sent in lieu of a response.</p>

<h4 id="response-9">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----usersusernamementions"><code class="language-plaintext highlighter-rouge">GET     users/@{username}/mentions</code></h3>
<p>Retrieves all (non-deleted) tweets in which the user with the given username is mentioned. The tweets should appear in reverse-chronological order. If no active user with that username exists, an error should be sent in lieu of a response.</p>

<p>A user is considered “mentioned” by a tweet if the tweet has <code class="language-plaintext highlighter-rouge">content</code> and the user’s username appears in that content following a <code class="language-plaintext highlighter-rouge">@</code>.</p>

<h4 id="response-10">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----usersusernamefollowers"><code class="language-plaintext highlighter-rouge">GET     users/@{username}/followers</code></h3>
<p>Retrieves the followers of the user with the given username. Only active users should be included in the response. If no active user with the given username exists, an error should be sent in lieu of a response.</p>

<h4 id="response-11">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----usersusernamefollowing"><code class="language-plaintext highlighter-rouge">GET     users/@{username}/following</code></h3>
<p>Retrieves the users followed by the user with the given username. Only active users should be included in the response. If no active user with the given username exists, an error should be sent in lieu of a response.</p>

<h4 id="response-12">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tags"><code class="language-plaintext highlighter-rouge">GET     tags</code></h3>
<p>Retrieves all hashtags tracked by the database.</p>

<h4 id="response-13">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Hashtag</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tagslabel"><code class="language-plaintext highlighter-rouge">GET     tags/{label}</code></h3>
<p>Retrieves all (non-deleted) tweets tagged with the given hashtag label. The tweets should appear in reverse-chronological order. If no hashtag with the given label exists, an error should be sent in lieu of a response.</p>

<p>A tweet is considered “tagged” by a hashtag if the tweet has <code class="language-plaintext highlighter-rouge">content</code> and the hashtag’s label appears in that content following a <code class="language-plaintext highlighter-rouge">#</code></p>

<h4 id="response-14">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tweets"><code class="language-plaintext highlighter-rouge">GET     tweets</code></h3>
<p>Retrieves all (non-deleted) tweets. The tweets should appear in reverse-chronological order.</p>

<h4 id="response-15">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="post----tweets"><code class="language-plaintext highlighter-rouge">POST    tweets</code></h3>
<p>Creates a new simple tweet, with the author set to the user identified by the credentials in the request body. If the given credentials do not match an active user in the database, an error should be sent in lieu of a response.</p>

<p>The response should contain the newly-created tweet.</p>

<p>Because this always creates a simple tweet, it must have a <code class="language-plaintext highlighter-rouge">content</code> property and may not have <code class="language-plaintext highlighter-rouge">inReplyTo</code> or <code class="language-plaintext highlighter-rouge">repostOf</code> properties.</p>

<p><strong>IMPORTANT:</strong> when a tweet with <code class="language-plaintext highlighter-rouge">content</code> is created, the server must process the tweet’s content for <code class="language-plaintext highlighter-rouge">@{username}</code> mentions and <code class="language-plaintext highlighter-rouge">#{hashtag}</code> tags. There is no way to create hashtags or create mentions from the API, so this must be handled automatically!</p>

<h4 id="request-5">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span>
  <span class="nl">content</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">credentials</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h4 id="response-16">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----tweetsid"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}</code></h3>
<p>Retrieves a tweet with a given id. If no such tweet exists, or the given tweet is deleted, an error should be sent in lieu of a response.</p>

<h4 id="response-17">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="delete--tweetsid"><code class="language-plaintext highlighter-rouge">DELETE  tweets/{id}</code></h3>
<p>“Deletes” the tweet with the given id. If no such tweet exists or the provided credentials do not match author of the tweet, an error should be sent in lieu of a response. If a tweet is successfully “deleted”, the response should contain the tweet data prior to deletion.</p>

<p><strong>IMPORTANT:</strong> This action should not actually drop any records from the database! Instead, develop a way to keep track of “deleted” tweets so that even if a tweet is deleted, data with relationships to it (like replies and reposts) are still intact.</p>

<h4 id="request-6">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h4 id="response-18">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="post----tweetsidlike"><code class="language-plaintext highlighter-rouge">POST    tweets/{id}/like</code></h3>
<p>Creates a “like” relationship between the tweet with the given id and the user whose credentials are provided by the request body. If the tweet is deleted or otherwise doesn’t exist, or if the given credentials do not match an active user in the database, an error should be sent. Following successful completion of the operation, no response body is sent.</p>

<h4 id="request-7">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="post----tweetsidreply"><code class="language-plaintext highlighter-rouge">POST    tweets/{id}/reply</code></h3>
<p>Creates a reply tweet to the tweet with the given id. The author of the newly-created tweet should match the credentials provided by the request body. If the given tweet is deleted or otherwise doesn’t exist, or if the given credentials do not match an active user in the database, an error should be sent in lieu of a response.</p>

<p>Because this creates a reply tweet, content is not optional. Additionally, notice that the <code class="language-plaintext highlighter-rouge">inReplyTo</code> property is not provided by the request. The server must create that relationship.</p>

<p>The response should contain the newly-created tweet.</p>

<p><strong>IMPORTANT:</strong> when a tweet with <code class="language-plaintext highlighter-rouge">content</code> is created, the server must process the tweet’s content for <code class="language-plaintext highlighter-rouge">@{username}</code> mentions and <code class="language-plaintext highlighter-rouge">#{hashtag}</code> tags. There is no way to create hashtags or create mentions from the API, so this must be handled automatically!</p>

<h4 id="request-8">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">{</span>
  <span class="nl">content</span><span class="p">:</span> <span class="dl">'</span><span class="s1">string</span><span class="dl">'</span><span class="p">,</span>
  <span class="nx">credentials</span><span class="p">:</span> <span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
<span class="p">}</span>
</code></pre></div></div>

<h4 id="response-19">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="post----tweetsidrepost"><code class="language-plaintext highlighter-rouge">POST    tweets/{id}/repost</code></h3>
<p>Creates a repost of the tweet with the given id. The author of the repost should match the credentials provided in the request body. If the given tweet is deleted or otherwise doesn’t exist, or the given credentials do not match an active user in the database, an error should be sent in lieu of a response.</p>

<p>Because this creates a repost tweet, content is not allowed. Additionally, notice that the <code class="language-plaintext highlighter-rouge">repostOf</code> property is not provided by the request. The server must create that relationship.</p>

<p>The response should contain the newly-created tweet.</p>

<h4 id="request-9">Request</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Credentials</span><span class="dl">'</span>
</code></pre></div></div>

<h4 id="response-20">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----tweetsidtags"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/tags</code></h3>
<p>Retrieves the tags associated with the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p><strong>IMPORTANT</strong> Remember that tags and mentions must be parsed by the server!</p>

<h4 id="response-21">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Hashtag</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tweetsidlikes"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/likes</code></h3>
<p>Retrieves the active users who have liked the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p>Deleted users should be excluded from the response.</p>

<h4 id="response-22">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tweetsidcontext"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/context</code></h3>
<p>Retrieves the context of the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p><strong>IMPORTANT:</strong> While deleted tweets should not be included in the <code class="language-plaintext highlighter-rouge">before</code> and <code class="language-plaintext highlighter-rouge">after</code> properties of the result, transitive replies should. What that means is that if a reply to the target of the context is deleted, but there’s another reply to the deleted reply, the deleted reply should be excluded but the other reply should remain.</p>

<h4 id="response-23">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="dl">'</span><span class="s1">Context</span><span class="dl">'</span>
</code></pre></div></div>

<h3 id="get-----tweetsidreplies"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/replies</code></h3>
<p>Retrieves the direct replies to the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p>Deleted replies to the tweet should be excluded from the response.</p>

<h4 id="response-24">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tweetsidreposts"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/reposts</code></h3>
<p>Retrieves the direct reposts of the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p>Deleted reposts of the tweet should be excluded from the response.</p>

<h4 id="response-25">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">Tweet</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>

<h3 id="get-----tweetsidmentions"><code class="language-plaintext highlighter-rouge">GET     tweets/{id}/mentions</code></h3>
<p>Retrieves the users mentioned in the tweet with the given id. If that tweet is deleted or otherwise doesn’t exist, an error should be sent in lieu of a response.</p>

<p>Deleted users should be excluded from the response.</p>

<p><strong>IMPORTANT</strong> Remember that tags and mentions must be parsed by the server!</p>

<h4 id="response-26">Response</h4>
<div class="language-javascript highlighter-rouge"><div class="highlight"><pre class="highlight"><code><span class="p">[</span><span class="dl">'</span><span class="s1">User</span><span class="dl">'</span><span class="p">]</span>
</code></pre></div></div>
