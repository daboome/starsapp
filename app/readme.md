This is a concept-proof StarsApp

To clone the application to your working directory type

git clone https://github.com/daboome/starsapp.git

To build the application type within your working directory

cd starsapp; ./gradlew assemble

To install application on modile device type wihtin your worling directory

cd starsapp/app/build/outputs/apk; adb install app-debug.apk

CAUTION:
This application requires starback backend server to be started first.