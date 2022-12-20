rm -rf build/dependency
chmod +x ./gradlew
./gradlew build -x test
mkdir -p build/dependency
cd build/dependency
jar_path=`ls ../libs/*-SNAPSHOT.jar 2> /dev/null`
jar xf ${jar_path}
cd ../..
docker build --tag $1 .