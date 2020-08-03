-- Set sequence values to be used in the project_id column so that they are not the same as the data values input by GSP.
ALTER SEQUENCE client.client_client_id_seq RESTART WITH 100
