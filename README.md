1.  Typeahead suggestions enable users to search for known and frequently searched terms. As the user types into the search box, it tries to predict the query based on the characters the user has entered and gives a list of suggestions to complete the query. Typeahead suggestions help the user to articulate their search queries better. It’s not about speeding up the search process but rather about guiding the users and lending them a helping hand in constructing their search query. What data structure would be used for the same and how?

Using Trie, we can implement Type ahead suggestion system. Below mentioned are the high level functionality methods:
insert - For each character of the input, it will form a node. Each node can have max of 26 arrays as sub-nodes.
search - Perform DFS for preorder traversal and retrieving the words from the dictionary which starts with the user input character. Also this event will be recorded in a flag and a counter is set to ind the maximum searched words.
count  - Obtaining the results based on the frequency is implemented by converting the suggestions into dynamic dictionary which has the counts for each attempt. Based on this counter we can sort descending and capture the trending words.

-----------------------------
2.  Design an API Rate limiting system that monitors the number of requests per a window time(i.e. could be second/hour etc) a service agrees to allow. IF the number of requests exceeds the allowed limit the rate limiter should block all excess calls.
System should be designed considering the following:
	a)  Rate limiting should work for a distributed set up as the APIs are available through a group of API Gateways - We can store the timestamp & counter of window in a redis sorted set along with Sliding window counter. Incase if we have multiple request in the same timeband, it is feasible.
	b) What database would be used and the rationale behind the choice c) how would throttling be done d) the system should be highly available - Please refer the API_RateLimiter.docx document in the root directory.
-----------------------------

3.   How would you design a garbage collection system? The idea here is to design a system that recycles unused memory in the program. Here the key is to find which piece of memory is unused. What data structure would be chosen and how would it be used to ensure garbage collection does not halt the main process?

Using Graph data structure & Mark and Sweep algorithm. Below are the operations performed:
get(Object) : Creates a new Object to the graph - release(Object) : To indicate that unused object - garbageCollector() : initializes the garbage collection 
initializeGC identifies unused references and enqueue it to finalize queue. Finalize method will be called to process objects and push it to finalize queue. objRef refers the  node of graph.
Use cases covered: avoiding cyclic references during traversal. works for both objects with or without finalize method. non blocking implementation of finalize.

