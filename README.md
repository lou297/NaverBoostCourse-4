# NaverBoostCourse-4
네이버 부스트코스 강의 실습 프로젝트 4번부터

## 4. Navigation View 활용과 ViewPager 
*ViewPager 구현*
Main에서 프래그먼트들을 담은 리스트를 생성, 초기화

ViewPagerAdapter의 Constructor에 프래그먼트 리스트를 전달해줘 구현해 주었다.
### *앱의 구조*
MainActivity의 FrameLayout안에 ViewPager가 Fragment로 담겨 있다.

ViewPager의 내용을 Fragment View로 생성하였기 때문에,

메인 뷰는 (*Activity -> ViewPager Fragment -> Movie View Fragment*) 로 중첩되어 있다.

## 5. 서버에서 영화 정보 가져오기
### library 사용
1. 서버 요청은 Volley를 통해서 보내준다.
2. 응답 받은 Json 포맷을 이용해주기 위해 Gson을 이용한다.
3. 이미지 주소를 통해 이미지 불러오기는 Glide를 사용해주었다.

### Activity에서 영화 목록 ViewPager생성 과정
1. activity oncreate시에 서버로 요청을 보내 영화 정보들을 받아놓는다.
2. 영화 정보는 Gson을 통해 DataClass ArrayList 변수로 갖고 있으며 DataClass에 Parcelable를 구현해줌으로 객체로 데이터 전송 가능하게 해준다.
3. ViewPager Fragment에는 전체 영화 목록을 전달해주며, ViewPager Fragment에서 각각의 영화 View Fragment에게 하나의 영화 정보를 전달해준다.
  이때 데이터는 모두 Parcelable을 구현해준 DataClass 객체를 bundle을 통해 전달해준다.
4. 각각의 View들은 전달 받은 영화 정보들을 이용해 View를 생성해준다.

### Movie Detail View 생성
Movie Detail View는 Fragment이며 MainActivity위에 올라간다.

MainActivity안 ViewPager View안에 있는 버튼을 클릭 시 Detail View를 보여줘야 하므로 생성 과정들은 MainAcitivty를 통해 이루어 진다.

이는 MainActivity 위에서 영화 목록과 영화 상세 목록을 모두 보여줌을 의미하는데, 두 경우 모두 서버에서 정보를 받아와야 하기 때문에, Activity에 있는 Request Method를 두 Fragment에서 같이 이용할 수 있도록 해주었다. 이를 위해 어느 Fragment에서 Method를 호출한 지 구분할 수 있도록 인자를 추가해 주었다.
