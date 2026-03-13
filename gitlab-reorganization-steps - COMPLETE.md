# GitLab Repository Reorganization

## Overview
Splitting the `OOP-GameTesting` GitLab project into separate projects and reorganizing the `Coding` directory.

**Target structure:**
```
Coding/
  Pokemon-OOP/       ← git → gitlab.com/calabriel/Pokemon-OOP
  W3Schools/         ← git → gitlab.com/calabriel/W3Schools
  bottest/           ← git → gitlab.com/calabriel/bottest
  WGU_Capstone/      ← git → gitlab.com/calabriel/WGU_Capstone
  BatchScripts/
  ScriptedInstaller.ps1
```

---

## Prerequisites

### Create new GitLab projects (blank, no README)
- `Pokemon-OOP`
- `W3Schools`
- `bottest`

### Rename existing GitLab project
- `test` → `WGU_Capstone`
  - GitLab → `test` project → Settings → General → rename path to `WGU_Capstone`

### Install git-filter-repo (required for Step 1)
```powershell
pip install git-filter-repo
```
> Note: On Windows, git-filter-repo is installed as a Python module. Use `python -m git_filter_repo` instead of `git filter-repo`.

---

## Step 1: Extract `Pokemon-OOP` with full git history

```powershell
cd "C:\Users\jthubbard\OneDrive - Randolph Community College\Documents\Coding"

# Clone a fresh copy (do NOT use the working copy in VS Code)
git clone https://gitlab.com/calabriel/OOP-GameTesting.git Pokemon-OOP-temp
cd Pokemon-OOP-temp

# Rewrite history to only contain the Pokemon-OOP subdirectory, moved to root
python -m git_filter_repo --subdirectory-filter Pokemon-OOP

# Point it at the new GitLab project and push
git remote add origin https://gitlab.com/calabriel/Pokemon-OOP.git
git push -u origin main

# Clean up the temp clone
cd ..
Remove-Item -Recurse -Force Pokemon-OOP-temp

# Clone the final version locally
git clone https://gitlab.com/calabriel/Pokemon-OOP.git
```

---

## Step 2: Create `W3Schools` (no history, sourced from OOP-GameTesting)

```powershell
cd "C:\Users\jthubbard\OneDrive - Randolph Community College\Documents\Coding"

# Copy the folder from OOP-GameTesting (parent .git is not copied)
Copy-Item -Recurse "OOP-GameTesting\W3Schools" "W3Schools"
cd W3Schools

git init
git remote add origin https://gitlab.com/calabriel/W3Schools.git
git add .
git commit -m "Initial commit"
git push -u origin main
```

---

## Step 3: Initialize `bottest` repo

```powershell
cd "C:\Users\jthubbard\OneDrive - Randolph Community College\Documents\Coding\bottest"

git init
git remote add origin https://gitlab.com/calabriel/bottest.git
git add .
git commit -m "Initial commit"
git push -u origin main
```

---

## Step 4: Rename `test` → `WGU_Capstone` and clean it up

```powershell
cd "C:\Users\jthubbard\OneDrive - Randolph Community College\Documents\Coding"

# Rename the folder
Rename-Item test WGU_Capstone
cd WGU_Capstone

# Remove the W3Schools subdirectory from this repo
Remove-Item -Recurse -Force W3Schools
git rm -r W3Schools
git commit -m "Remove W3Schools subdirectory"

# Update the remote to match the renamed GitLab project
git remote set-url origin https://gitlab.com/calabriel/WGU_Capstone.git
git push
```

---

## Step 5: Delete `OOP-GameTesting` locally

After confirming all projects pushed successfully:

```powershell
cd "C:\Users\jthubbard\OneDrive - Randolph Community College\Documents\Coding"
Remove-Item -Recurse -Force OOP-GameTesting
```

Then on GitLab, archive or delete the `OOP-GameTesting` project:
- Settings → General → Advanced → Archive or Delete

---

## Notes
- `git filter-repo` must be run on a fresh clone, not the working copy open in VS Code.
- `W3Schools` inside `test/` (now `WGU_Capstone/`) is removed in Step 4 — it is not migrated.
- `Pokemon-OOP` history is fully preserved via `filter-repo`.
- `W3Schools` and `bottest` start with a clean initial commit (no prior history).
