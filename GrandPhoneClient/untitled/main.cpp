#include <iostream>


void f(int a, int* ans) {
  ans[0] = 0;
  int i;
  for (i = 2; i * i <= a; ++i)
    if (a % i == 0) {
      ans[0] += 1;
      ans[1] = i;
    }

}

int main() {
  int * ans = new int [2];
  for (int i = 123456789; i < 223456790; ++i) {
    f(i, ans);
    if (ans[0] == 3) {
      std::cout << i << ' ' << ans[1] << '\n';
    }
  }

    return 0;
}

