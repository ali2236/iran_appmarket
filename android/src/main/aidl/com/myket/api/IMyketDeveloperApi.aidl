package com.myket.api;

interface IMyketDeveloperApi {
    int isDeveloperApiSupported(int apiVersion);

    Bundle getAppUpdateState(int apiVersion, String packageName);

    int isUserLogin(int apiVersion);

    Bundle getLoginIntent(int apiVersion);
}