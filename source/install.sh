#!/bin/bash
javac -cp .:lib/mysql-connector-j-9.1.0.jar:lib/jbcrypt-0.4.jar -d bin src/User.java src/ImperiumMotoring.java src/UserRegistration.java src/UserLogin.java src/Utils.java
java -cp .:bin:lib/mysql-connector-j-9.1.0.jar:lib/jbcrypt-0.4.jar ImperiumMotoring
