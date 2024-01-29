import sys
from sympy import *
from sympy.parsing.sympy_parser import parse_expr

x = Symbol('x')
op = sys.argv[1]

a = nsimplify(parse_expr(sys.argv[2]))
if op == 'sqrt':
    ans = sqrt(a)
elif op == 'coeffs':
    ans = poly(a, x).all_coeffs()
elif op == 'deg':
    ans = degree(a, x)
elif op == 'simplify':
    ans = simplify(a)
else:
    b = nsimplify(parse_expr(sys.argv[3]))
    ans = None
    if op == 'add':
        ans = Add(a, b)
    elif op == 'sub':
        ans = Add(a, Mul(-1, b))
    elif op == 'div':
        ans = Mul(a, Pow(b, -1))
    elif op == 'sc_mul':
        ans = Mul(a, b)
    elif op == 'int':
        start, end = (sys.argv[4], sys.argv[5])
        start = nsimplify(start)
        end = nsimplify(end)
        ans = integrate(a*b, (x, start, end))

print(ans)