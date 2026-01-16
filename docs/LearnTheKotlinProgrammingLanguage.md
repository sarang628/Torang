# [Learn the Kotlin programming language](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2)

## [변수 선언](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2#variables)
- val - 변경 불가
- var - 변경 가

## [타입 추론](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2#inference)
- 초기값을 할당하면 코틀린 컴파일러가 이를 추론

## [Null safety](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2#null-safety)
- 코틀린 변수는 기본적으로 null 허용이 안됨.
- 타입 뒤에 ?를 붙여야 null 할당 가능

## [조건문](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2#conditionals)
- if, else, else if
- stateful한 로직을 표현하는데 유용
- if else를 변수를 할당하는데도 사용 가능하며, 조건문에 마지막 값을 선언하면 return을 안써도 해당 값으로 할당 됨.
- 코틀린은 삼항 연산자 지원 안함.
- 조건이 많아 질때 유용한 when 이란 조건 연산자 지원
- 코틀린 조건문에 흥미로운 기능 smart cast
  - if 조건에서 null 체크 시, 조건문 안에서 자동으로 not null 타입으로 변환해 줌.
  - 스마트 캐스팅은 널 체크, 타입체크 가능

## [함수](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#functions)
- 동일한 그룹의 표현식들을 써야 한다면 이를 묶어서 함수로 만들 수 있다.
- fun keyword 사용
- arguments를 입력값으로 받을 수 있음
- [함수 정의 간소화](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#simplifying)
  - 결과가 하나의 표현식을 그대로 return 하는 경우, 변수 선언 등을 생략할 수 있음.
  - return 을 할당 연산자로 사용하여 간소화 할 수도 있음.
- [익명 함수](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#anonymous)
  - 함수가 너무 단순해 입출력 값만 봐도 어떤 기능인지 명확하게 알 수 있는 경우
  - 함수를 정의하지 않고 **타입**처럼 쓸 수 있는 익명 함수로 생성 가능
  - 익명 함수를 어딘가에 저장해두고 참조하고 있는 **함수를 변수처럼 사용**가능.
- [고차 함수](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#higher-order)
  - 함수를 파라미터로 사용 가능
  - 자바의 콜백 방식을 따라하는데 유용]

## [클래스](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#classes)
- [property](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#properties)
  - 클래스의 상태는 property를 사용해 나타낸다.
  - property는 클래스단의 변수
  - car 라는 클래스가 있으면 차가 달리기 위해 가지고 있는 wheel은 property
- [함수와 캡슐화](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#class-functions)
  - 클래스의 동작을 모델링하기위해 함수를 사용
  - 함수로 상태를 수정 가능
  - 함수로 보여주고 싶은 데이터만 노출 가능
  - 이런 기능들을 커다란 개념에서 캡슐화라고 함.

## [상호운용성](https://developer.android.com/kotlin/learn?_gl=1*1cr6o35*_up*MQ..*_ga*OTE3OTkxODgwLjE3Njg0NjI2NTA.*_ga_6HH9YJMN9M*czE3Njg0NjI2NDkkbzEkZzAkdDE3Njg0NjI2NDkkajYwJGwwJGgxMjEzNjMwNzM2&utm_source=android-studio-app&utm_medium=app#interoperability)
- 코틀린의 중요한 특징중 하나는 자바와의 상호운용성
- 코틀린은 JVM 바이트 코드로 컴파일 됨.
- 이는 상호간에 선언 및 호출이 가능
- 기존의 자바 라이브러리를 활용 가능
- 안드로이드 주요 API들이 자바로 개발되었기 때문에 이를 바로 사용할 수 있다.


Q. 프로그래밍에서 expressions(표현식?) 이란?
 [wikipedia](https://en.wikipedia.org/wiki/Expression_(computer_science))