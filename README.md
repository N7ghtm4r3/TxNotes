# TxNotes
**v1.0.4**

This is a Java Based library useful to take note about your transactions that you have made on the biggest crypto
exchanges and analyse your crypto-wallet trend. Good use! <br>
This library is based on <a href="https://github.com/N7ghtm4r3/TraderBot/blob/main/README.md">TraderBot<a/>, take a
look!

## Implementation

Add the JitPack repository to your build file

### Gradle

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- Add the dependencies

```gradle
dependencies {
	implementation 'com.github.N7ghtm4r3:TxNotes:1.0.4'
	//to make TxNotes works correctly you must implement also the main library:
	implementation 'com.github.N7ghtm4r3:TraderBot:1.1.1'
}
```

### Maven

- Add it in your root build.gradle at the end of repositories

```xml

<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

- Add the dependencies

```xml

<dependency>
  <groupId>com.github.N7ghtm4r3</groupId>
  <artifactId>TxNotes</artifactId>
  <version>1.0.4</version>
</dependency>
```
###### To make TxNotes works correctly you must implement also the main library:
	
```xml
<dependency>
    <groupId>com.github.N7ghtm4r3</groupId>
  <artifactId>TraderBot</artifactId>
  <version>1.1.1</version>
</dependency>
```

## 🛠 Skills

- Java

## Platforms available

- **Binance**
- **Coinbase**

The other platforms will be gradually released

## Usage/Examples

**Note:** your exchange platform api keys **will not be** stored anywhere in our system, so is your responsibility
manage them.

### Native use

Fetchers work without Tecknobit's Android interface

#### Interface

You have to create your code flow to use fetcher

```java
//Platform means Binance or Coinbase actually
PlatformFetcher fetcher = new PlatformPlatformFetcher(/*your keys and constructor params*/);
//Operation example
fetcher.fetchTxNotesList();
```

#### Autonomous

Fetchers fetch autonomously all the newest transactions

```java
//Platform means Binance or Coinbase actually
PlatformAutoFetcher fetcher = new PlatformAutoFetcher(/*your keys and constructor params*/);
//Start autonomous trader flow 
fetcher.start();
```

### Android use

Fetchers work with <a href="https://play.google.com/store/apps/details?id=com.tecknobit.txnotes">Tecknobit's Android
interface </a><br>
All data will be stored and restored automatically, **will be not saved** your exchange platform api keys. <br>
**Note:** is recommended to use different password from exchange's account for Tecknobit's account for a major security.

#### Auth operations

- #### Registration
  To register a new fetcher you have to use Credentials object to init your Tecknobit's account credentials.
  In first attempt will throw a SaveData exception, so you can save credentials generated.

```java
//In registration, you have to init TxNotesCredentials object with your email and password for Tecknobit's account.

TxNotesCredentials credentials=new TxNotesCredentials("yourEmail@email.com","yourPassword");

//Program will automatically stop and will give to you auth credentials in this format:

/*     
    {
      "secret_key": "valueOfSecretKey",
      "password": "yourPasswordInserted",
      "email": "yourEmailInserted@email.com",
      "iv_spec": "valueOfIvSpec",
      "auth_token": "valueOfAuthToken",
      "token": "valueOfToken"
    }
*/

//Note you must save these credentials where you retain safe because them are needed in auth operations.
```

- #### Login
  In login operation you have to use TxNotesCredentials object to init your Tecknobit's account credentials.

```java
//In login operation you have to init TxNotesCredentials object with your credentials given from Registration

TxNotesCredentials credentials=new TxNotesCredentials("valueOfAuthToken",
        "yourEmail@email.com",
        "yourPassword",
        "valueOfIvSpec",
        "valueOfSecretKey"
        );

//Then you have to insert credentials object in Android's trader constructor and trader will start with your Tecknobit's account.

AndroidPlatformFetcher fetcher = new AndroidPlatformFetcher(/*your keys and constructor params*/,credentials);

```

#### Run operations

It is very simple to start the chosen fetcher in order to start its workflow autonomously, this means that all
operations will be performed by itself, following the procedure:

```java
//Platform means Binance or Coinbase actually
AndroidPlatformFetcher fetcher = new AndroidPlatformFetcher(/*your keys and constructor params*/,credentials);
//Operation example
fetcher.startWorkflow();
```

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/TxNotes/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Coinbase-0052FF?style=for-the-badge&logo=Coinbase&logoColor=white)](https://docs.cloud.coinbase.com/commerce/docs)
[![](https://img.shields.io/badge/Binance-FCD535?style=for-the-badge&logo=binance&logoColor=white)](https://binance-docs.github.io/apidocs/spot/en/#general-api-information)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

[![](https://jitpack.io/v/N7ghtm4r3/TxNotes.svg)](https://jitpack.io/#N7ghtm4r3/TraderBot)
## Donations

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">
PayPal</a>

## Privacy policy

This section concerns Privacy and policy for Android's application published on Google Play Store. <br>
TxNotes application **will not share any personal data of the user with third part applications.** <br>
All data about Tecknobit's account, so not **api keys of exchanges platforms**, will be store
in <a href="https://firebase.google.com/">Firebase's services</a>
and application performance and session will be tracked by <a href="https://analytics.google.com/">Google Analytics
services</a>. <br>
So all data saved concern TxNotes service offered by Tecknobit and **any other data** will be saved. <br>
Regards and good use!

Copyright © 2022 Tecknobit
