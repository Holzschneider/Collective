# Collective
Allocation is futile: Atypical trivial & non-trivial collection classes.

A set of custom collection classes that have been missing in Java 1.6's java.util.Collections.
* optimized for low memory footprint and allocation avoidance.
* collections based on algorithmic geometry
* collections related to concurrency and multi-threading



Release
-------

The base release 1.x.y corresponds to the unmodified collections implementation, as they've been extracted from legacy projects.


Releases are deployed automatically to the deploy branch of this github repostory. 
To add a dependency on *Collective* using maven, modify your *repositories* section to include the git based repository.

	<repositories>
	 ...
	  <repository>
	    <id>dualuse Repository</id>
	    <name>dualuse's Git-based repo</name>
	    <url>https://dualuse.github.io/maven/</url>
	  </repository>
	...
	</repositories>
	
and modify your *dependencies* section to include the *Fancy* dependency
 
	  <dependencies>
	  ...
	  	<dependency>
	  		<groupId>de.dualuse</groupId>
	  		<artifactId>Collections</artifactId>
	  		<version>[1,)</version>
	  	</dependency>
	  ...
	  </dependencies>


To add the repository and the dependency using gradle refer to this

	repositories {
	    maven {
	        url "https://dualuse.github.io/maven"
	    }
	}

and this

	dependencies {
	  compile 'de.dualuse:collective:1.0.+'
	}
