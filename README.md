2024-09-06 
- Slf4j Logger library install<br>
pom.xml에 Slf4j library를 등록해서 logger 기록할 수 있도록 작업함<br>
꼭 변경 후에는 mvn clean install 수행d
- Client의 요청 정보를 BufferReader를 통해서 읽어서 화면에 출력함<br>
입력을 읽을 때 무한 루프에 빠질 수 있으니 조심해야 함.

2024-09-07
- 요청 URL에 해당하는 파일 webapp 디렉토리에서 읽어 전달하도록 구현함.
