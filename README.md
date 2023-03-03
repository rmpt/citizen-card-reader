# Citizen Card Reader - PT

Runnable app to read Portuguese Citizen Card data from a browser (or any HTTP client). This app comes from a previous 
version of mine, that would only run on Windows and required a lot of configuration (Chrome Extension, JS lib, local 
app installation and the Autenticacao.gov app installed).

This new version does **not** require any of these apps. This is an HTTP server bundled with all required dependencies, 
so you just need to run this app and request data through the API.

## How to get data

The API spec can be found [here](https://rmpt.dev/spec?api=ccard).

In short, you get 2 endpoints:

- `/ccard/readers`
  - Get a list of connected card readers
- `/ccard/data?reader=<your_reader>&fields=<field_to_get>`
  - Get data from the card inserted into the given reader 

## Security

This app does not cover any kind of advanced security, it was designed to run in a private computer. Use it at your own risk.

However, there's a couple of configurations you can use to make it a little more secure:
- CORS configuration
- Require some custom header

### CORS and HTTP header example

By default, there's no CORS or custom header requirement, but you can tweak these configurations by setting an
`application.yml` file along with the jar file.

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

The app will read this configuration and apply it on every request.

## Linux users

Windows and MacOS users shouldn't have any problem running the app without installing any additional software.  

For Linux users, you will need to have installed the following dependencies:
- libxerces-c3.2
- libxml-security-c20
- libzip4
- opensc-pkcs11
- libpam-pkcs11
- pcscd

Install all at once with apt:

`apt-get install -y libxerces-c3.2 libxml-security-c20 libzip4 opensc-pkcs11 libpam-pkcs11 pcscd`


## Compatibility

As mentioned before, this should work on MacOS, Windows and Linux, tests were made on:
- Windows 10
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
the api is a simple HTTP interface on top of the lib, so it's not required for other types of implementation.

## Pteid lib

This is a personal project, but the low level library that communicates with the citizen card is called pteid and is cloned 
from the 3.9.1 version of the app [Autenticação.gov](https://www.autenticacao.gov.pt)  