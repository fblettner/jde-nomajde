# ARG0 : Mode (XML / PDF)
# ARG1 : Object NAME
# ARG2 : Version
# ARG3 : Language
# ARG4 : JOB Number
# ARG1, ARG2, ARG3 are use to rename the output filename, you can set this as per your needs

# EXTRACT XML SOURCE
java -cp ../dist/nomajde.jar jdebip XML R014021 ZJDE0001 FR 7606236

# EXTRACT PDF OUTPUT
java -cp ../dist/nomajde.jar jdebip PDF R014021 ZJDE0001 FR 7606236
