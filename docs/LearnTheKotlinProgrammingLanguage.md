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

Q. 프로그래밍에서 expressions(표현식?) 이란?
 [wikipedia](https://en.wikipedia.org/wiki/Expression_(computer_science))