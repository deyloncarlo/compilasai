int a;
boolean b, c;
string d = "casa";
byte e;
final f = 3;

{
	write, "pessoa";
	writeln, d, e, f;

	k = 7;

	read, a;
	read, b;

	if (a >= f) a = f*5;

	if (a == 0) 
		e = 12;
	else {
		e = 13;
	}

	while (e <= 100)
	{
		e = e + 2;
		if ((e / 2) > ((e/2)*2))
			e = e + 1;
	}
}
