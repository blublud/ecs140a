#!/bin/python
startname="acdt93"
endname="kcn1989"

import os
from distutils import dir_util

for d in walk("/home/cs140a/handin/hw2"):
	if (d >= startname and d <= endname):
		os.system("tar -xzvf " + "/home/pdphuong/" + d + "hw2.zip" )
