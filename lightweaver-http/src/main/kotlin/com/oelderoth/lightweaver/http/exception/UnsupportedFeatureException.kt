package com.oelderoth.lightweaver.http.exception

class UnsupportedFeatureException(message: String, val apiVersion: Int): RuntimeException(message) {
    constructor(newType: String, category: String, apiVersion: Int): this("$newType is not a supported value for $category", apiVersion)
}