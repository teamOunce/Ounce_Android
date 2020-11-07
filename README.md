# OUNCE

## What Is OUNCE?
> 똑똑한 집사들을 위한 기록장 Ounce.
>
> 고양이들은 입맛이 까다로워, 집사들은 성공확률이 높은 시도를 위해 먹여본 캣푸드를 따로 기록하고 있습니다.
> 저희는 이러한 집사들의 고민을 해결하기 위해 직관적인 기록, 서로의 목록 공유, 입맛이 비슷한 고양이 추천 기능을 제공하고 있습니다.
>
> 기록부터 선택까지, 온스가 함께합니다.

## NYAN-DORID
### Fascinating Member
- **이현우**
    - Ounce/Maru 안드로이드 개발
    - Ounce의 Android Lead
    - 끊임없는 성장을 추구합니다
- **강민구**
    - Crecker/Ounce/Sangle 안드로이드 개발
    - 26기 SOPT Android MVP
    - 27기 SOPT Android 파트장
    - 실력/외모/인성 3박자를 고루 갖춘 Ounce 안드로이드 팀의 정신적 지주
- **박주예**
    - Ounce 안드로이드 개발
    - Ounce의 **독보적인** 귀여움 담당

## OUNCE's Commit Convention
### Basic Struture
```
type: subject

body(optional)

footer(optional)
```

### Type
- feat: 새로운 기능
- fix: 버그 수정
- docs: 문서 수정
- style: 스타일 변경(코드 변경 X)
- refactor: 리팩토링
- test: 테스트 코드 추가/테스트 리팩토링
- chore: updating build tasks, package manager 설정

### Subject
- Subject는 50글자를 넘어가면 안된다
- 첫 시작은 대문자로 해야한다
- 마지막에 마침표(.)를 찍으면 안된다
- 어떤 변경점이 있는지 설명한다
- 명령조를 사용한다

### Body
- 부연 설명이나 커밋의 이유를 설명할 때만 사용
- Not How, Explauin What and Why
- 윗 부분과 1줄의 공백 필요
- 각 라인은 72자 초과 불가

### Footer
- Issue Tracker IDs를 적을 때 사용

## OUNCE's Coding Convention For Clean Code
### Kotlin Style Guide
OUNCE는 [Google의 Kotlin Coding Style Guide](https://developer.android.com/kotlin/style-guide)를 따릅니다

### Class Layout
다음과 같은 순서 클래스를 구성합니다
- Property 선언과 초기화 블럭(intializer blocks)
- 추가적인 생성자
- 메소드 정의
- 컴패니언 오브젝트(Companion object)

### Naming Rule
#### Package
- 패키지의 이름은 항상 소문자로 하고, 밑줄을 사용하지 않습니다
- 두 개 이상의 단어를 한 번에 사용하는 것을 금지합니다

#### Class/Object
- Pascal Case
```
open class SampleName { /* ... */ }
object MoreSampleName : SampleName() { /* ... */ }
```

#### Function/Parameter/Variable
- Camel Case
```
val initList = mutableList<SampleData>()
fun getList: List<SampleData>() { /* ... */ }
```
#### Constant
- Upper Snake Case
- 상수는 companion objet에 넣어 보관합니다
```
companion object {
    const val MAX_COUNT = 8
}
```

### Formatting
#### Indenting
Tab 키를 써서 Indenting 합니다

#### 중괄호
괄호 뒤에 한 칸을 띄우고 사용합니다
```
if (elements != null) {
    for (element in elements) {
        // ...
    }
}
```

#### 괄호
- Control문(if/while/for)
    - 한 칸 띄어씁니다
- 생성자/Method
    - 붙여씁니다
```
if (isSpacing == true) { /*...*/ }
fun isSpacing() { /*...*/ }
```

#### Colon(:)
- 변수의 타입/함수 리턴 타입 결정
    - 콜론을 앞에 붙인다
- 상속받은 클래스/인터페이스 구분
    - 한 칸 띄어쓴다
```
fun isSpacing(): Boolean { /*...*/ }
class MainActivity : AppCompatActivity()
```

## Open Source Library

라이브러리                                                                                                                          | 목적
---------------------------------------------------------------------------------------------------------------------------------- | -----------------------------------------------------------------
[DotIndicator](https://github.com/tommybuonomo/dotsindicator?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7127) | 뷰페이저 인디케이터
[Activity-KTX](https://developer.android.com/jetpack/androidx/releases/activity)                                                   | Activity에서 ViewModel 초기화위해 사용
[Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)                                                   | Fragment간 화면 전환 용이
[Kakao SDK](https://developers.kakao.com/docs/latest/en/getting-started/sdk-android)                                               | Kakao Login 구현
[Retrofit2](https://github.com/square/retrofit)                                                                                    | 서버 통신
[Gson](https://github.com/google/gson)                                                                                             | 서버에서 받아온 Json 객체를 Gson으로 변환
[OkHttp](https://square.github.io/okhttp/)                                                                                         | HTTP 통신
[EncryptedSharedPreference](https://developer.android.com/jetpack/androidx/releases/security)                                      | 중요 정보 암호화하여 기기 내에 저장
[TedKeyboardObserver](https://github.com/ParkSangGwon/TedKeyboardObserver)                                                         | 키보드 show/hide 리스너
