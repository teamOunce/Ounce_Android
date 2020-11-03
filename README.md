# OUNCE

## What Is OUNCE?
> 똑똑한 집사들을 위한 기록장 Ounce.
>
> 고양이들은 입맛이 까다로워, 집사들은 성공확률이 높은 시도를 위해 먹여본 캣푸드를 따로 기록하고 있습니다.
> 저희는 이러한 집사들의 고민을 해결하기 위해 직관적인 기록, 서로의 목록 공유, 입맛이 비슷한 고양이 추천 기능을 제공하고 있습니다.
>
> 기록부터 선택까지, 온스가 함께합니다.

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
