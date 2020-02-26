package ir.aligator.appmarket.exceptions

import java.lang.Exception

class FeatureNotSupportedException(val marketName: String, val featureName: String)
    : Exception("market $marketName does not support $featureName")