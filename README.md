# Cecula SMS

- [Cecula SMS](#cecula-sms)
  - [Introduction](#introduction)
  - [Library Usage](#library-usage)
  - [How to Generate an API Key](#how-to-generate-an-api-key)
  - [Importing Cecula Library](#importing-cecula-library)
  - [Sending A2P SMS](#sending-a2p-sms)
  - [Sending P2P SMS](#sending-p2p-sms)
  - [Checking A2P SMS Balance](#checking-a2p-sms-balance)
  - [Checking Sync Cloud Balance](#checking-sync-cloud-balance)
  - [Error Response](#error-responses)

----------

## Introduction

Cecula SMS Java Library enables you to quickly integrate and send A2P and P2P Messages from your application.

## Library Usage

 * Download the cecula_message_v1.0.0.jar file
 * Add the jar file to your build path/libraries folder depending on your IDE.
 
  

## How to Generate an API Key

Your API Key is first generated when you register an app. To register an app,
Login to the Developers Dashboard, Navigate to __Apps > Add__, Type the name of your app and click **Submit**. The app will be registered and a new API Key will be generated. Copy the API key into your project.
Click [developer.cecula.com](https://developer.cecula.com/docs/introduction/generating-api-key) to get started.

## Importing Cecula Library

import the library into your Java application using the code below
```sh
...
import com.cecula.messaging.CeculaSMS;
...
```

## Sending A2P SMS

To send SMS with alphanumeric identity to single or multiple contacts, use the code below:
```sh
    String[] recipients = {"234809xxxxxxx"};
    String sender = "Cecula";
    String message = "Hello world";
    CeculaSMS cSMS = new CeculaSMS(<YOUR_API_KEY>);
    cSMS.sendA2PMessage(sender, message, recipients);
```
Your response should look like this:
 ```sh
    {
        "status": "sent",
        "reference": "4982953",
        "sentTo": [ "234809xxxxxxx" ],
        "invalid": [],
        "declined": [],
        "declineReason": "",
        "code": "1801"
    }
```

## Sending P2P SMS

To send a message using numberic identity, use the code below:
 ```sh
 
    String[] recipients = {"2349090000246","2349090000271"};
    String sender = "2348050209037";
    String message = "Hello world";
    CeculaSMS cSMS = new CeculaSMS(<YOUR_API_KEY>);
    cSMS.sendP2PMessage(sender, message, recipients);
```
Your response should look like this:
```sh
    {
        "status": "sent",
        "code": "1801",
        "messageID": "2579",
        "sentTo":[
            {
                "recipient": "2349090000246",
                "id": "5990"
            },
            {
                "recipient": "2349090000271",
                "id": "5991"
            }
        ],
        "declined": []
    }
```

## Checking A2P SMS Balance

To get your A2P SMS Balance, __getA2PBalance__ method is used this way:
```sh
    CeculaSMS cSMS = new CeculaSMS(<YOUR_API_KEY>);
    cSMS.getA2PBalance();
```

You should get a response like this:
```sh
    {
        "balance": 234.1
    }
```

## Checking Sync Cloud Balance

To get your Sync Cloud Balance, __getSyncCloudBalance__ method is used this way:
This method requires a single parameter - your registered sync cloud identity on the Cecula platform:
```sh
   String identity = "2348050209037"
   CeculaSMS cSMS = new CeculaSMS(<YOUR_API_KEY>);
   cSMS.getSyncCloudBalance(identity);
```
You should get a response like this
```sh
    {
        "balance": 9513
    }
```

## Error Responses

In a case where the request fails due to one reason or another you should get an error response from the requested endpoint that looks like this:
```sh
        {
            "error": "Invalid PIN Ref",
            "code": "CE2000"
        }
```
The table below shows a list of error codes and their descriptions:

| Error Code | Description     |
|:---------:| :--------------|
| CE1001	| Missing Fields |
| CE1002	| Empty Fields |
| CE1003	| Origin cannot be longer than 11 characters |
| CE1004	| A2P Message origin must be alphabets only or alphanumeric |
| CE1005	| Message cannot be longer than 10 pages |
| CE1007	| Cannot find the identity on Sync Cloud |
| CE1008	| Origin is pending verification |
| CE1009	| Account Unbound. Please Recharge Account and Contact Sales |
| CE1010	| Numeric Originator must be between 5 - 16 digits long |
| CE1011	| P2P Message originator must be numeric |
| CE1012	| Origin is not verified |
| CE1013	| Sync App is Offline. Please check device |
