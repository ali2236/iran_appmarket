enum AppMarket{
  cafeBazaar,
  myket,
  jhoobin,
  iranApps,
}

String appMarketName(AppMarket appMarket){
  const names = {
    AppMarket.cafeBazaar : 'CafeBazaar',
    AppMarket.myket : 'Myket',
    AppMarket.jhoobin: 'Jhoobin',
    AppMarket.iranApps: 'IranApps'
  };
  return names[appMarket];
}