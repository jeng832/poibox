# Contributing to poibox

poibox 프로젝트에 기여해주셔서 감사합니다! 이 문서는 프로젝트에 기여하는 데 도움이 되는 가이드라인을 제공합니다.

## 시작하기

*   **코드 가져오기:** 먼저 `main` 브랜치에서 최신 코드를 가져옵니다.
    ```bash
    git clone https://github.com/jeng832/poibox.git
    cd poibox
    ```
*   **이슈 확인:** 작업하기 전에 GitHub Issues에서 관련 이슈가 있는지 확인하거나 새로운 이슈를 생성하여 논의하는 것이 좋습니다.

## 브랜치 정책

우리는 `main` 브랜치를 중심으로 Gitflow 전략을 약간 단순화한 모델을 사용합니다.

### 핵심 브랜치

*   **`main`**: 항상 안정적이고 배포 가능한 상태의 코드를 유지합니다. 직접적인 커밋은 금지되며, 오직 `release` 또는 `hotfix` 브랜치의 병합만 허용됩니다. 모든 릴리즈 버전은 이 브랜치에서 태그됩니다.
*   **`feature/*`**: 새로운 기능 개발, 리팩토링, 중요하지 않은 버그 수정 등을 위한 브랜치입니다. `main`에서 분기하여 작업하고, 완료 후 `main`으로 Pull Request(PR)를 보냅니다. (예: `feature/add-new-feature`, `feature/ISSUE-123-fix-bug`)
*   **`release/*`**: 새로운 프로덕션 릴리즈를 준비하기 위한 브랜치입니다. `main`에서 분기하며, 버전 번호 업데이트, 최종 테스트, 문서 수정 등 릴리즈 관련 작업을 수행합니다. 완료 후 `main`으로 병합하고 태그를 생성합니다. (예: `release/1.0.0`)
*   **`hotfix/*`**: 배포된 `main` 브랜치의 긴급한 버그를 수정하기 위한 브랜치입니다. `main`에서 분기하여 작업하고, 완료 후 `main`과 현재 진행 중인 `release` 브랜치(필요시)에 병합합니다. (예: `hotfix/critical-bug-fix`)

### 개발 워크플로우 (`feature` 브랜치)

1.  최신 `main` 브랜치에서 새로운 `feature` 브랜치를 생성합니다. (`git checkout -b feature/your-feature-name origin/main`)
2.  해당 브랜치에서 기능을 개발하고 작업 단위별로 커밋합니다. (커밋 메시지 가이드라인 참고)
3.  (권장) 개발 중 주기적으로 `origin/main` 브랜치를 `rebase`하여 최신 상태를 유지합니다. (`git fetch origin`, `git rebase origin/main`)
4.  작업 완료 후 원격 저장소에 브랜치를 푸시합니다. (`git push -u origin feature/your-feature-name`)
5.  `main` 브랜치를 대상으로 Pull Request(PR)를 생성합니다.
6.  코드 리뷰를 받고 피드백을 반영합니다.
7.  리뷰 승인 및 CI 통과 후 PR을 `main` 브랜치에 병합합니다. (Squash and merge 권장)
8.  병합 후에는 `feature` 브랜치를 삭제합니다.

### 릴리즈 워크플로우 (`release` 브랜치)

1.  릴리즈할 시점의 `main` 브랜치에서 `release` 브랜치를 생성합니다. (`git checkout -b release/version-number origin/main`)
2.  `release` 브랜치에서 버전 번호 업데이트, 최종 테스트, 문서 작업 등을 수행하고 커밋합니다.
3.  준비 완료 후 `release` 브랜치를 `main` 브랜치로 병합합니다. (`git checkout main`, `git merge --no-ff release/version-number`)
4.  `main` 브랜치의 병합 커밋에 버전 태그를 생성하고 푸시합니다. (`git tag -a vX.Y.Z -m "Release version X.Y.Z"`, `git push origin vX.Y.Z`)
5.  (선택) `release` 브랜치는 유지하거나 삭제할 수 있습니다.
6.  Maven Central 등 필요한 곳에 배포합니다.
7.  진행 중인 다른 `feature` 브랜치에 최신 `main` 변경 사항을 반영합니다. (rebase)

## 커밋 메시지 가이드라인

커밋 메시지는 [Conventional Commits](https://www.conventionalcommits.org/) 형식을 따르는 것을 권장합니다.

*   **유형**: `feat`(기능), `fix`(버그 수정), `docs`(문서), `style`(코드 스타일), `refactor`(리팩토링), `test`(테스트), `chore`(빌드, 기타 잡일)
*   **형식**: `<유형>[범위(선택)]!: <제목>` (예: `feat: Add support for vertical headers`, `fix(parser): Correct date parsing logic`)

## Pull Request (PR) 프로세스

*   PR은 `main` 브랜치를 대상으로 생성합니다.
*   PR 제목과 본문에 변경 내용을 명확하게 설명합니다. (무엇을, 왜 변경했는지)
*   관련 이슈가 있다면 본문에 링크합니다. (`Closes #123`)
*   코드 리뷰를 요청하고 피드백을 성실히 반영합니다.
*   모든 검사(CI) 통과 및 리뷰 승인 후 PR을 병합합니다. `feature` 브랜치의 경우 **Squash and merge** 방식을 권장합니다.

## 버그 리포트 및 기능 제안

*   버그를 발견하거나 새로운 기능을 제안하고 싶다면 GitHub Issues를 사용해주세요.
*   이슈를 생성하기 전에 유사한 이슈가 있는지 먼저 검색해주세요.

감사합니다! 