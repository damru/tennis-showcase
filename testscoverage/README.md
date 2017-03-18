# Kata Tennis

A sample for tests coverage implementation using maven profiles :
* _**always run UT**_
* **test** : run IT
* **quality** : (rerun tests ?) generate reports for UT and IT

## Technical Stack :
* Unitary Tests
    * maven surefire
* Integration Tests
    * maven failsafe
* Coverage
    * jacoco (to generate reports for SONAR)
        * 1 report for UT (jacoco-ut.exec)
        * 1 report for IT (jacoco-it.exec)