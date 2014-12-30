#!/bin/bash

sbt "project skinnySplash" "+publishSigned" "project skinnySplashServlet" "+publishSigned" "sonatypeRelease"

