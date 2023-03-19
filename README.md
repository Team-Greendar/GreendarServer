# GreendarServer

Greendar Project Server Repository

### 브랜치 관리 전략

Git Flow를 사용하여 브랜치를 관리합니다.<br>
Release,Develop 브랜치는 Pull Request에 리뷰를 진행한 후 merge를 진행합니다.<br>
메인 브렌치인 Develop인 경우 리뷰와 PR 을 필수로 하는 깃 protrction이 설정되어 있습니다.<br><br>

<br>
<br><br>
![image](https://user-images.githubusercontent.com/46917538/72450182-44475300-37fd-11ea-8a1b-ecce20fd6fcb.png)
<br><br>

- Release : 배포시 사용합니다.
- Develop : 완전히 개발이 끝난 부분에 대해서만 Merge를 진행합니다.
- Feature : 기능 개발을 진행할 때 사용합니다.
- Hot-Fix : 배포를 진행한 후 발생한 버그를 수정해야 할 때 사용합니다.
- Main : v1.0.0 , v1.1.0 과 같이 2번째 자리수의 버전 까지를 저장합니다.
  <br><br>
  <b>브랜치 관리 전략 참고 문헌</b><br>
- 우아한 형제들 기술 블로그(http://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)
- Bitbucket Gitflow Workflow(https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)
