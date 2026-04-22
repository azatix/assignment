# API Tests for License Service

Automated API tests for License Service using Kotlin, Ktor client and JUnit 5.
Supports positive and negative scenarios, divided in two groups for changeLicensesTeam and licensesAssign

## Environment variables

Before running tests, please set:

```
export API_KEY=valid_api_key
export CUSTOMER_CODE=valid_customer_code 
```

## Quick Start

``
./gradlew test
``

### Run tests for changeLicensesTeam only

``
./gradlew test -PincludeTag=changeLicensesTeamTests
``

### Run tests for assignLicenses only

``
./gradlew test -PincludeTag=licensesAssignTests
``

### Allure report

``
./gradlew allureServe
``

