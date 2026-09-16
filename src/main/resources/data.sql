INSERT INTO usuario (username, password) 
VALUES ('admin', '$2a$10$gHeKP6iSWpeCPuCt2nh/luviNg6PRhgqxFkZI/MttJbPCSANu9O9K') 
ON CONFLICT (username) DO NOTHING;
