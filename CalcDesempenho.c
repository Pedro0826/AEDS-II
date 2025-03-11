#include <stdio.h>

int main(){

  int Tp;
  int P;
  float mQui;
  int mFei;
  int resp;
  float total;

  printf("Digite o valor da soma de suas 2 melhores provas: ");
  scanf("%d", &P);
  printf("Digite a média dos TPs: ");
  scanf("%d", &Tp);

  printf("Voce fez todos os quizzes? (Digite 1 para SIM e 2 para NAO): ");
  scanf("%d", &resp);

  if(resp == 1){
    printf("Digite a média dos quizzes: ");
    scanf("%f", &mQui);

    total = 15 * ((float)P / 40) * ((float)Tp / 20) * ((1 + (((mQui * 30) / 100))/100));
    printf("Seu desempenho é: %.2f\n", total);
  }
  else if(resp == 2){
    printf("Digite quantos quizzes voce fez: ");
    scanf("%d", &mFei);

    printf("Digite a média dos quizzes: ");
    scanf("%f", &mQui);

    total = 15 * ((float)P / 40) * ((float)Tp / 20) * (((((float)mFei * 100) / 11) + ((mQui * 30) / 100))/100);
    printf("Seu desempenho é: %.2f\n", total);
  }

  return 0;
}
