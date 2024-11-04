OLD_EMAIL = "santonopoulou93@gmail.com"
CORRECT_NAME = "Sophia Antonopoulou"
CORRECT_EMAIL = "s.willowood@acg.edu"

def rewrite_author_info(commit):
    if commit.author_email == OLD_EMAIL:
        commit.author_name = CORRECT_NAME
        commit.author_email = CORRECT_EMAIL
    if commit.committer_email == OLD_EMAIL:
        commit.committer_name = CORRECT_NAME
        commit.committer_email = CORRECT_EMAIL
