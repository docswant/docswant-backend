REPOSITORY=/home/ubuntu/app/deploy
cd $REPOSITORY

APP_NAME=docswant
JAR_NAME=$(ls $REPOSITORY | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 실행중인 애플리케이션 없음"
else
  echo "> 실행중인 애플리케이션을 종료"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_NAME 실행권한 추가"

chmod +x $JAR_NAME

echo "> 새 애플리케이션 배포"

nohup java -jar -Dspring.profiles.active=dev $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
