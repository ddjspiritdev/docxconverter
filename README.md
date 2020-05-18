The project's goal is to create a Restfull Webservice to convert docx file to pdf. 
To start the project it is necessary tto create a postgres database first.
One of the easier way to do it is to run a Docker command like: 
docker run --name some-postgres -p5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=document_DB -d postgres.
