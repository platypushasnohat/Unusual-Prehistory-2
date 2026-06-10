@echo off
setlocal enabledelayedexpansion

set "IMAGEMAGICK=C:\Program Files\ImageMagick-7.1.2-Q16-HDRI\magick.exe"
set "INPUT_FOLDER=D:\UserData\Aadil\Desktop\GitHub\Unusual-Prehistory-2\src\main\resources\assets\unusual_prehistory\textures\gui\paleopedia\images\mob_renders"
set "COLOR=d3c294"

pushd "%INPUT_FOLDER%"

for %%F in (*.png) do (

    echo %%~nF | findstr /i "_unknown$" >nul
    if errorlevel 1 (

        set "OUTPUT=%%~nF_unknown.png"

        if not exist "!OUTPUT!" (

            echo Processing %%~nxF

            "%IMAGEMAGICK%" "%%F" -alpha extract temp_alpha.png
            "%IMAGEMAGICK%" "%%F" -fill "#%COLOR%" -colorize 100 temp_colored.png
            "%IMAGEMAGICK%" temp_colored.png temp_alpha.png -compose CopyOpacity -composite "!OUTPUT!"

            del temp_alpha.png
            del temp_colored.png

        ) else (
            echo Skipping %%~nxF -- unknown already exists
        )
    )
)

popd
echo Done.
pause