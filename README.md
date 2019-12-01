# Exercises: OpenIMAJ: Intelligent Multimedia Analysis in Java

This repository implements the exercices of the book [OpenIMAJ: Intelligent Multimedia Analysis in Java](http://openimaj.org/tutorial-pdf.pdf) 
by Jonathon Hare, Sina Samangooei and David Dupplaw.

There is a branch for each chapter.
   - 1. Getting started with OpenIMAJ using Maven - master
   - 2. Processing your first image - CHAPTER-02

# Create an OpenIMAJ project with Maven

To create a new OpenIMAJ project, run the following command:

´´´
mvn -DarchetypeGroupId=org.openimaj -DarchetypeArtifactId=openimaj-quickstart-archetype \
-DarchetypeVersion=1.3.9 archetype:generate
´´´

Maven will then prompt you for some input. For the groupId, enter something that identifies you or a group that you belong to
(for example, I might choose uk.ac.soton.ecs.jsh2 for personal projects, or org.openimaj for OpenIMAJ sub-projects). For
the artifactId enter a name for your project (for example, OpenIMAJ-Tutorial01). The version can be left as 1.0-SNAPSHOT,
and the default package is also OK. Finally enter Y and press return to confirm the settings. Maven will then generate a new project
in a directory with the same name as the artifactId you provided.