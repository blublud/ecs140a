#include <stdio.h>
main()
{
int _e0_i;
int _e0_k;
_e0_i=25;
while(10-_e0_i <= 0)
{
if (20-_e0_i <= 0)
{
_e0_k=20-_e0_i;
while(_e0_k <= 0)
{
printf("%d\n",_e0_k);
_e0_k=_e0_k+1;
}
}
else if(15-_e0_i <= 0)
{
_e0_k=15-_e0_i;
while(_e0_k <= 0)
{
int _e3_long;
_e3_long=10*_e0_k;
printf("%d\n",_e3_long);
_e0_k=_e0_k+1;
}
}
else {
_e0_k=10-_e0_i;
while(_e0_k <= 0)
{
printf("%d\n",_e0_k);
_e0_k=_e0_k+1;
}
}
_e0_i=_e0_i-1;
}
printf("%d\n",_e0_i);
}
