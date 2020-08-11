# SROC TCM Acceptance tests

[![Build Status](https://travis-ci.com/DEFRA/sroc-tcm-acceptance-tests.svg?branch=main)](https://travis-ci.com/DEFRA/sroc-tcm-acceptance-tests)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DEFRA_sroc-tcm-acceptance-tests&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=DEFRA_sroc-tcm-acceptance-tests)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DEFRA_sroc-tcm-acceptance-tests&metric=coverage)](https://sonarcloud.io/dashboard?id=DEFRA_sroc-tcm-acceptance-tests)
[![Known Vulnerabilities](https://snyk.io/test/github/DEFRA/sroc-tcm-acceptance-tests/badge.svg)](https://snyk.io/test/github/DEFRA/sroc-tcm-acceptance-tests)
[![Licence](https://img.shields.io/badge/Licence-OGLv3-blue.svg)](http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3)

> Temporary project. Do not use as a basis for doing selenium tests with Java!

The Strategic Review of Charging (SROC) Tactical Charging Module (TCM) is an internal tool built by Defra to support the calculation of charges related to various permitting regimes.
 
This project contains the current acceptance tests for the TCM. It is built around [Selenium Webdriver](https://www.selenium.dev/documentation/en/webdriver/), a tool that allows you to programmatically 'drive' a browser.

## Prerequisites

- [Java 8](https://openjdk.java.net/install/)
- [Browser binaries](#installing-webdriver-binaries) (see below)

The project supports running the 'test' using [Firefox](https://www.mozilla.org/en-GB/firefox/) or [Chrome](https://www.google.com/intl/en_uk/chrome/) so you'll need at least one of these browsers installed.

## Installation

> These instructions assume you are using a Unix-based system, for example Linux or Mac OSX

Clone the repository, copying the project into a working directory

```bash
git clone https://github.com/DEFRA/sroc-tcm-acceptance-tests.git
cd sroc-tcm-acceptance-tests
```

Then build the project using [Maven](https://maven.apache.org/). To simplify things the project comes with [maven-wrapper](https://github.com/takari/maven-wrapper). This will automatically install Maven for you if you don't already have it. 

```bash
./mvnw clean install
```

> Windows users can use `mvnw.cmd clean install`

### Installing WebDriver binaries

Selenium is actually a range of tools. To be able to drive the browser we use them along with others we need to obtain for ourselves.

Selenium provides the API for controlling the browser, for example `get("http://example.com")`, `findElement().getText()`, and `findElement().click()`. These commands control binaries the browser makers provide. Selenium provides the drivers for those binaries, but it is on us to obtain them.

#### Chrome

First check the version of Chrome you are using by checking its 'About page'. Then [download the version chromedriver](https://chromedriver.storage.googleapis.com/index.html) which most closely matches your Chrome version and operating system.

You'll download a zip file so once extracted move the **chromedriver** binary to the `drivers/` folder of this project.

#### Firefox

[Download the latest version of geckodriver](https://github.com/mozilla/geckodriver/releases) for your operating system.

You'll download a zip file so once extracted move the **geckodriver** binary to the `drivers/` folder of this project.

### Confirm setup

From the root of the project run the following to confirm all is working

```bash
TCM_TEST_PASSWORD=password java -jar target/sroc-tcm-acceptance-tests.jar example.config.yml
```

If it is you should see Chrome open, and a quick search on Google take place. Blink, and you might miss it!

## Configuration

To run the main test create a new config file. For example, create a file called `dev.chrome.config.yml`, then add the following

```yaml
browser: chrome
rootUrl: "https://url-of-tcm-dev-environment.gov.uk/"
test: main
```

You can create as many configuration files as you like mixing `browser` and `rootUrl`.

```yaml
browser: firefox
rootUrl: "https://url-of-tcm-test-environment.gov.uk/"
test: main
```

Key things are

- `browser:` only use the values `chrome` or `firefox`
- `rootUrl:` should be the base url for an environment, not something like `https://url-of-tcm-dev-environment.gov.uk/auth/sign_in`
- `test:` should always be `main`. It's configurable only to support the [Confirm setup](#confirm-setup) step of the installation.

### Committing config files

At this time the config files are not considered sensitive. So we do not ignore them. This means when added git will see a change has been made. If they will be used repeatedly and will be of use to others feel free to commit them to the project.

## Password

The 'test' relies on being able to login. So it needs to know the password to use. We don't want this accidentally committed. So we **do not** include it as a value in the `*.config.yml` file.

Instead, you will need to set an environment variable before running the 'test'. You can do this in a number of ways

- as part of the command to run the 'test' `TCM_TEST_PASSWORD=password java -jar ...`
- on Unix-based systems add it to your `~/.bash_profile` or `~/.zshrc` as `export TCM_TEST_PASSWORD=password`
- on Windows [use the environment variables dialog](https://www.computerhope.com/issues/ch000549.htm)
- in your IDE. For example, in Intellij IDEA you can create [run configurations](https://www.jetbrains.com/help/idea/run-debug-configuration.html) where you can set any environment variables that are needed

## Execution

The project requires a path to the `*.config.yml` file to be passed as the one and only argument.

```bash
java -jar target/sroc-tcm-acceptance-tests.jar dev.chrome.config.yml
```

The path can be absolute (`/Users/acruikshanks/sroc-tcm-acceptance-tests/dev.chrome.config.yml`) or relative (`configs/dev.chrome.config.yml`).

## Here be dragons!

The current team inherited the 'test' contained in this project. We were handed a single Java file containing 2.6K lines of code. Turns out it's a single `public static void main()` method (😱🤯) which uses Selenium to navigate the TCM.
  
No testing framework is referenced so there are no assertions. The 'test' was just *can I get through to the end without erroring*. Other highlights were

- hardcoded paths for a specific user's machine
- hardcoded to work on Windows only
- included an encrypted password along with the logic to decrypt it 🤦
- no documentation on how to use it
- no packaging or build tool. You needed to manually locate and download the Jar's referenced. To run you then needed to manually setup Eclipse (as it used Eclipse features to run)
- Riddled with `Thread.sleep(1000);` so lots of waiting around

Suffice to say we found this unmaintainable! So we created this project to make it possible to run the 'test' cross platform, on any machine and not require users to manually build their environment.

As such we don't expect anyone else to reuse this project, and we will be migrating away from it. We would also not recommend using it as a reference on how to setup a Selenium based project in Java using Maven. There must be better ones out there!

## Contributing to this project

If you have an idea you'd like to contribute please log an issue.

All contributions should be submitted via a pull request.

## License

THIS INFORMATION IS LICENSED UNDER THE CONDITIONS OF THE OPEN GOVERNMENT LICENCE found at:

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3>

The following attribution statement MUST be cited in your products and applications when using this information.

>Contains public sector information licensed under the Open Government license v3

### About the license

The Open Government Licence (OGL) was developed by the Controller of Her Majesty's Stationery Office (HMSO) to enable information providers in the public sector to license the use and re-use of their information under a common open licence.

It is designed to encourage use and re-use of information freely and flexibly, with only a few conditions.