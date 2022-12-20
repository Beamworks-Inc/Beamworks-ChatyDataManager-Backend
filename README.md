# SpringKotlinTemplate
[![codecov](https://codecov.io/gh/BEOKS/SpringKotlinTemplate/branch/main/graph/badge.svg?token=75JRE4VYJ1)](https://codecov.io/gh/BEOKS/SpringKotlinTemplate)
[![api page](https://img.shields.io/badge/Spring_Rest_Docs-main-007396?logo=Read+the+Docs&logoColor=white)](https://htmlpreview.github.io/?https://github.com/BEOKS/SpringKotlinTemplate/blob/main/src/main/resources/static/docs/api-dcs.html)


새로운 Kotlin 기반의 Spring 프로젝트를 시작할 때 사용할 템플릿 레포지토리입니다.
# Contents
- [1.Introduction](#1introduction)
- [2. Usage](#2-usage)
  * [2.1 Test Coverage Verification](#21-test-coverage-verification)
  * [2.2 Test Code Exclusion](#22-test-code-exclusion)
  * [2.3 Private Repository](#23-private-repository)
  * [2.4 Spring Rest Docs Badge](#24-spring-rest-docs-badge)
- [3.Feature](#3feature)
  * [3.1 Test](#31-test)
  * [3.2 Test Coverage](#32-test-coverage)
    + [3.2.1 Exclude Test Code](#321-exclude-test-code)
    + [3.2.2 Test Coverage Report](#322-test-coverage-report)
    + [3.2.3 Code Coverage Badge](#323-code-coverage-badge)
  * [3.3 Spring Rest Docs](#33-spring-rest-docs)
  * [3.4 Create Docker Image](#34-create-docker-image)
  * [3.5 Convention](#35-convention)
# 1.Introduction
새로운 프로젝트를 개발하고 관리하기 위해서는 테스트, 커버리지 , 문서작성 그리고 코드 컨벤션 등 프로그래밍 이외의 작업이 굉장히 많습니다. 프로젝트를 개발할 때마다 이들을 일일이 설정, 자동화하는 것은 매우 번거롭습니다. SpringKotlinTemplate은 이를 해결하기 위해 모범사례를 바탕으로 구성된 프로젝트 템플릿입니다. 새로운 프로젝트를 시작할 때 이 템플릿을 이용해서 개발에 필요한 설정과정을 생략해서 빠르게 개발을 진행할 수 있습니다.

> ❕ Continuous Delivery를 구성하지 않은 이유
>
> Continuous Delivery 는 각자의 환경에 따라 방법이 다양하게 나뉩니다. 배포한경이 클라우드 인지 온프레미스인지, 클라우드에서도 AWS, Azure, CloudFare 등 다양한 서비스로 나뉘게 되고 배포 방법도 SSH, AWS CodeDeploy, CircleCI 등 다양한 방법이 존재합니다. 그리고 배포파일 또한 JAR, WAR, Docker image 등 다양한 형식으로 나뉘기 때문에 템플릿에서 일반화된 구성이 어렵습니다. 대신, 현재 대부분의 서비스가 도커 이미지를 통해 배포되고 있는 만큼 여기에는 도커 이미지 빌드 구성이 적용되어 있습니다.

# 2. Usage
이 템플릿은 예시 설정으로 구성되어 있습니다. 따라서 자신의 프로젝트에 맞는 설정이 필요합니다.
## 2.1 Test Coverage Verification
[3.2 Test Coverage](#32-test-coverage) 에서는 단순히 테스트 커버리지만 측정하는 것이 아니라 
코드 길이, 최소 커버리지 등의 확인 기능도 구현되어있습니다. 이를 수정하기 위해서는 ```build.gradle.kts```에서
```violationRules``` 옵션을 수정해야합니다. 아래는 현재 라인 길이를 제한하는 설정 예시입니다. [JacocoViolationRule](https://docs.gradle.org/current/javadoc/org/gradle/testing/jacoco/tasks/rules/JacocoViolationRule.html)을
참고해서 다양한 규칙을 생성할 수 있습니다.
```kotlin
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }
        }
    }
}
```
## 2.2 Test Code Exclusion
테스트가 필요하지 않는 코드 설정은 이 [설정](#321-exclude-test-code)을 참고해주세요.
## 2.3 Private Repository
만약 개인 레포지토리에 이 템플릿을 사용하는 경우 CodeCov 를 사용하기 위해서는 토큰을 설정해야합니다.
CodeCov -> Setting 에서 발급 가능한 토클을 Github Secret 에 등록한 후 아래와 같이 [CI 워크 플로우](.github/workflows/test_coverage_update.yml)에
토큰 값을 추가해주세요. 공개된 레포지토리의 경우 CodeCov에서 Github을 이용해 로그인만하면 레포지토리를 연결할 수 있습니다.
```yaml
...
- name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3.1.1
      with:
        token: ${{ secrets.CODECOV_TOKEN }}
        file: ./build/reports/jacoco/test/jacocoTestReport.xml
...
```
## 2.4 Spring Rest Docs Badge
현재 레포지토리에선 Spring Rest Docs Badge 를 클릭해 문서를 확인할 수 있습니다. 하지만, 해당 뱃지에
 저장된 링크는 소유자, 레포지토리이름 그리고 브랜치이름 정보가 포함되어 있습니다. 따라서 새로운 레포지토리를
생성할 때는 이를 수정해주어야 합니다. 
```yaml
https://htmlpreview.github.io/?https://github.com/{소유자명}/{레포지토리명}/blob/{브랜치명}/src/main/resources/static/docs/api-dcs.html
```

# 3.Feature
## 3.1 Test
이 프로젝트에서는 Kotest와 mockk를 이용합니다.
## 3.2 Test Coverage
테스트 커버리지를 측정하기 위해서 JaCoCo(Java Code Coverage)를 사용합니다. 
새로운 PR을 생성하고 커밋이 갱신될때마다 테스트와 테스트 커버리지 체크가 수행됩니다. 
커버리지 체크가 수행된 후 README.md 의 커버리지 뱃지가 업데이트되며 클릭 시 
커버리지 Report에 접근할 수 있습니다.
### 3.2.1 Exclude Test Code
```SpringBootApplication``` 와 같이 테스트가 필요하지 않는 코드도 테스트 커버리지의 대상이 될 수 있습니다. 이를 차단하기 위해서 ```build.gradle.kts```에서 테스크 커버리지의 예외를 지정할 수 있습니다.
```kotlin
fun ConfigurableFileCollection.excludeSpringBootApplicationClass(){
    setFrom(
        files(files.map {
            fileTree(it) {
                exclude("com/example/springkotlintemplate/SpringKotlinTemplateApplication*")
            }
        })
    )
}
tasks.jacocoTestReport {
    reports {
        //...
    }
    classDirectories.excludeSpringBootApplicationClass()
}
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            classDirectories.excludeSpringBootApplicationClass()
            element = "CLASS"
            //...
        }
    }
}
```
### 3.2.2 Test Coverage Report
[CodeCov](https://about.codecov.io/)를 이용해서 커버리지 리포트를 확인할 수 있습니다. 
Github Action 을 활용해서 CI를 구축하여 자동으로 커버리티 리포트를 생성해 지속적인 업데이트가 가능합니다. 
1. CodeCov 가입
CodeCov 홈페이지에서 GitHub 계정을 통해 가입을 진행합니다.
2. Coverage Report HTML,XML 옵션 활성화

```build.gradle.kt```에서 아래와 같이 HTML,XML 옵션을 활성화합니다.
```kotlin
 tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true) //For CodeCoverage
        csv.required.set(false)
    }
    //...
}
```
3. Github Action 설정

PR을 생성하거나 브랜치에 푸시가 진행될때 마다 자동으로 코드 커버리지 테스트와 리포트 업데이트를 위한 Github Action을 설정합니다.
```yaml
name: Spring/Kotlin Coverage Test on main

on:
  push:
    branches: [ "main","dev","release" ]
  pull_request:
    branches: [ "main","dev","release" ]

permissions:
  contents: write

jobs:
  test:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        
    - name: Setup Gradle
      uses: gradle/gradle-build-action@v2
    
    - name: Build
      run: ./gradlew build
    
    - name: Test
      run: ./gradlew test
      
    - name: Create Test Coverage Report
      run: ./gradlew jacocoTestReport
      
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3.1.1
      with:
        file: ./build/reports/jacoco/test/jacocoTestReport.xml
```
### 3.2.3 Code Coverage Badge
마지막으로 코드 커버리지를 한눈에 파악하기 위한 뱃지를 등록할 수 있습니다.
CodeCov 에서 현재 선택한 Repository 에서 Settings 메뉴에 들어가면 CodeCov Badge를 사용할 수 있습니다.
## 3.3 Spring Rest Docs
API 문서를 자동화 하기 위해서 Spring Rest Docs를 이용합니다. 
빌드 시, 테스트 코드를 기반으로 api-docs.html파일을 생성합니다. 생성된 파일은 ```src/main/resources/api-dcs.html```에 저장됩니다. README.md의 뱃지를 클릭하면 해당 문서로 이동할 수 있습니다. 
> 🚧 Warn
> 
> 뱃지의 링크는 레포지토리명, 소유자 그리고 브랜치명이 포함되기 때문에 적절한 수정이 필요합니다.
## 3.4 Create Docker Image
프로젝트를 배포하기 위한 도커 이미지 빌드를 진행할 수 있습니다. 쉘 스크립트를 사용하면 그레이들 빌드를 수행하고
레이어별로 빌드 결과를 나누어 도커 이미지를 생성합니다. 이 방식을 통해서 빠른 시간안에 도커 이미지를 다시 빌드 할 수 있습니다.
쉘 스크립트에 태그를 입력하면 해당 태그 이름으로 이미지가 생성됩니다. 세부적인 도커 이미지 생성 옵션을 수정하기
위해서는 쉘 스크립트를 직접 수정해야합니다.
```shell
sh docker_image_build.sh spring-kotlin-template:1.0
```
## 3.5 Convention
코틀린 공식 사이트에서 제공하는 컨벤션을 활용합니다. 이 컨벤션은 Intellij 를 사용할 경우 Default 로 적용되어 있습니다.

