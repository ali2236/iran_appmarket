package ir.aligator.appmarket.exceptions

import java.lang.Exception

class FeatureNotImplementedException(val featureName: String) : Exception("$featureName is not yet implemented.")