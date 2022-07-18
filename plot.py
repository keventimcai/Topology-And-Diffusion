from ctypes import sizeof
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

plt.figure()
data=pd.read_csv("data.csv",sep=r"\t")
#node=pd.read_csv("degree.csv",sep=r"\n")


'''
vplot=np.polyfit(x,data['v'],deg=3)
print("these are the arguments of the regression")
print(vplot)
yvplot=np.polyval(vplot,x)
plt.plot(x,yvplot)
'''

plt.tight_layout()
plt.show()