OpenCover.Console.exe -register:user -target:"..\..\..\main\packages\xunit.runners.1.9.2\tools\xunit.console.clr4.x86.exe" -targetargs:"OpenCover.Test.dll /noshadow" -filter:"+[Open*]* -[OpenCover.T*]*" -output:xunit_opencovertests.xml -coverbytest:*.Test.dll
