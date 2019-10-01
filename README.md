# Korea Metro Graph Data

한국 도시 철도의 이전/다음역 및 노선번호 정보의 Graph 자료구조 표현을 위한 정점(Vertices), 간선(Edges) 데이터

현재는 서울시만 구현되어 있고, 다른 노선은 ~~언젠가~~ 구현 예정입니다.

## 이게 뭔가요? ~~먹는건가요?~~

[Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph)에서 파생된 프로젝트로, [Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph)에서 사용하는 데이터를 생성할 수 있고, 그 산출물만을 관리합니다.

해당 프로젝트를 만들게 된 목적은 두 가지입니다.

1. [서울시 역코드로 지하철역 정보 검색](http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-112&srvType=A&serviceKind=1) API의
늦은 업데이트. 새로운 노선이 생길때마다 공개 API만 의존하며 애플리케이션에서 대응하지 못하는 경우는 없어야겠죠? ~~일해라 서울교통공사!~~

2. 프로젝트 결합도(Coupling) 감소. [Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph)는 읽는 기능만 있을 뿐, 쓰는 기능이 없습니다. 매번 새로운 노선이 생기는 등의 작업을 수행할 때마다 [Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph) 프로젝트를 갱신할 이유가 전혀 없기 때문입니다. 
 
## 어떻게 이용하는건가요?

[Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph)에서만 이용할 수 있는 데이터는 아닙니다.
잘 가공하면 Database에서도 넣을 수 있는 (아마도?) 잘 가공된 JSON Data입니다.

`main/resources/seoul` 아래 파일들이 제공 됩니다.

### vertices.json

[서울시 역코드로 지하철역 정보 검색](http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-112&srvType=A&serviceKind=1) 공개 API의 JSON 파일을 가공한 파일입니다.

`2019.10.01` 기준으로 해당 API에서 지원하지 않는 `자기부상`, `서해`, `우이신설경전철` `김포골드라인` 노선 정보가 추가되어 있습니다. 하지만 공식정보가 아닌 관계로 몇몇의 정보가 누락되어 있습니다. 

또한 추가 기능으로 `병기역명(sub_station_nm)`이 모든 노선에 추가되어 있습니다.

#### vertices.min.json

[Korea Metro Graph](https://github.com/devwillee/KoreaMetroGraph)에서 사용할 수 있는 형태로 가공된, 필요한 정보만을 추출하여 압축한 데이터입니다.

각 요소의 명세는 아래와 같습니다.

| 필드 |  설명   |
|---|---|
| identifier | 현재역/이전역/다음역 구분. CURRENT/PREVIOUS/NEXT. |
| mainLine | 주노선 구분. true/false |
| station_nm | 역 이름 |
| sub_station_nm | 병기역명 |
| line_num | 노선 이름 |
| fr_code | 외부역 코드 |
| station_cd | 역 코드 |
| xpoint_wgs | 위도 (Latitude, WGS84) |
| ypoint_wgs |  경도 (Longitude, WGS84) |
| station_nm_han | 역 이름 (한자) |
| station_nm_eng | 역 이름 (영문) |
| sub_station_nm_han | 병기역명 (한자) |
| sub_station_nm_eng | 병기역명 (영문) |

`lineNum`은 아래와 같은 값을 가집니다. 이는 서울시 열린 데이터 광장의 서울시 역코드로 지하철역 정보 검색 API의 LINE_NUM 필드를 기준으로 작성됐습니다. (자기부상선, 서해선,  제외)
* 1 ~ 9 : 1 ~ 9호선
* A : 공항철도
* B : 분당선
* E : 에버라인 (용인경전철)
* G : 경춘선
* I : 인천1호선
* I2 : 인천2호선
* K : 경의중앙선
* KK : 경강선
* S : 신분당선
* SU : 수인선
* U : 의정부경전철
* UI : 우이신설경전철
* M : 자기부상선
* W : 서해선
* GG : 김포골드라인

##### vertices.min.pretty.json

`vertices.min.json`과 내용은 같은 파일로, 사람이 읽기 편한 형태로 가공된 파일입니다.

### edges.json

각 정점의 연결정보를 표현합니다. 각 요소의 명세는 아래와 같습니다.

| 필드 |  설명   |
|---|---|
| from | 출발역 정점 |
| to | 도착역 정점 |
| distance | 거리(Meter) |
| time | 시간(Minutes) |

#### edges.min.json

`edges.json`을 압축한 데이터입니다.

## 어떻게 데이터를 생성하나요?

1. 원본이 되는 `vertices.json`, `edges.json` 수정.
2. `test` 패키지에 있는 `Test.java` 이용.

```java
public class Test {
    public static void main(String[] args) throws IOException {
        Compressor.INSTANCE.compressVertices("src/main/resources/seoul/vertices.json");
        Compressor.INSTANCE.compressEdges("src/main/resources/seoul/edges.json");
    }
}
```
