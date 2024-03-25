# Citizen Card Reader - PT

Runnable app to read Portuguese Citizen Card data from a browser (or any HTTP client).

This app comes from a previous version that I made (a rebuild from scratch really), that would only run on Windows and 
required a lot of configuration (Chrome Extension, JS lib, local app installation and the Autenticacao.gov app installed).

This new version does **not** require ~~browser extension~~, ~~JS library~~ or ~~Windows~~. This is an HTTP server, 
running on the JVM, so you just need request data through the API.

## Requirements

- Java (min 1.8)
- Autenticacao.gov (https://www.autenticacao.gov.pt/web/guest/cc-aplicacao)
  - Last build with v3.12.0

## How to run the app

Download the latest release from [here](https://github.com/rmpt/citizen-card-reader/releases) or build your own jar 
and run it as a java app:

`java -jar citizen-card-reader-api.jar`

An installation wizard is not on the scope of this project. If you want to run this jar as a service, you can (and maybe 
it's the most common use-case) but you will have to build it yourself. It's pretty simple but varies from OS to OS, 
there's a lot of information about it online.

## How to get data

The API spec can be found [here](https://rmpt.dev/spec?api=ccard).

In short, you get 2 endpoints:

- `/ccard/readers`
  - Get a list of connected card readers
- `/ccard/data?reader=<reader_name>&fields=<field_to_get>`
  - Get data from the card inserted into the given reader 

Possible fields to fetch are:
- GivenName
- SureName
- Gender
- BirthDay
- TaxNumber
- SocialSecurityNumber
- CitizenNumber
- ExpirationDate
- Picture

You can request a single field, multiple comma separated fields (for instance `fields=GivenName,SureName,TaxNumber`) or `all`.

## How to build your own jar

If the release jar is not good for you, clone the repo and run (at the project root):

`./gradlew clean bootJar`

The jar will be packed and placed inside the dir: `/citizen-card-reader-api/build/libs`

## Custom configuration

If you don't need to tweak any configuration, ignore this section. If for some reason you need to override some
default configurations, create an `application.yml` file on the same dir of the runnable jar and set your configurations.

### Server port

By default the app will be available at `http://localhost:8080`, but you can change the port with the following 
configuration inside your `application.yml`.

```
server:
  port: 8081
```

### Security

This app does not cover any kind of advanced security, it was designed to run in a private computer. Use it at your own risk.

However, there's a couple of configurations you can use to make it a little more secure:
- CORS configuration
- Require some custom header

### CORS and HTTP header example

By default, there's no CORS or custom header requirement, but you can tweak these configurations with the
`application.yml` file.

1. Let's say you want to read some citizen card data from your webapp, running at `https://my-awesome-web-app.com`, and 
you want to reject any request outside this domain.
2. Apart from that, as a security measure, let's say you want to require some header on every request, that somehow 
"authenticates" the user, and this header is defined by `X-MY-SECRET-HEADER: mySecretKey`.

You can achieve that with the following `application.yml` file:
```
app:
  requiredHeader:
    name: X-MY-SECRET-HEADER
    value: mySecretKey
  cors: https://my-awesome-web-app.com
```

The app will read this configuration file and apply it on every request.

*NOTE*: you can set multiple CORS domains, separate them with a comma 
(`cors: https://my-awesome-web-app.com,https://another-web-app.com`)


## Compatibility

As mentioned before, this should work on any MacOS, Windows or Linux, specific tests were made on:
- Windows 10 (x86 and x64)
- MacOS Monterey 12.6.3
- Ubuntu 22.04.02 LTS

## Project structure

The project is divided in 2 modules:
- `citizen-card-reader-lib`
  - The actual interface between the host machine and the citizen card reader
- `citizen-card-reader-api`
  - HTTP interface on top of the citizen-card-reader-lib

With that being said, if the HTTP interface does not cover your needs, feel free to branch this project and use the
`citizen-card-reader-lib` module as needed. All core features are packed inside `citizen-card-reader-lib` module, 
the api is a simple HTTP interface on top of the lib, so it's not required for other type of implementation.
