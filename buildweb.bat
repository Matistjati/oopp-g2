cd .\dashboard\
call npm run build
cd ..
echo done
cd .\webserver\
call gradle installDist
cd ..

cd .\webserver\app\build\install\app
.\bin\app