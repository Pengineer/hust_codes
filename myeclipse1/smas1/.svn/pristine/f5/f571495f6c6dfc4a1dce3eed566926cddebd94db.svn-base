//字符串操作
//BothSidesTrim(string):去除字符串两边的空格
function BothSidesTrim(str){    
 	return str.replace(/(^\s*)|(\s*$)/g,"");
}

//AllTrim(string):去除字符串所有的空格
function AllTrim(str){    
 	return str.replace(/(\s*)/g,"");
}

//LTrim(string):去除左边的空格function LTrim(str)
{
       var whitespace = new String(" \t\n\r");
       var s = new String(str);
       if (whitespace.indexOf(s.charAt(0)) != -1)
       {
              var j=0, i = s.length;
              while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
              {
                     j++;
              }
              s = s.substring(j, i);
       }
       return s;
}
//RTrim(string):去除右边的空格function RTrim(str)
{
       var whitespace = new String(" \t\n\r");
       var s = new String(str);
       if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
       {
              var i = s.length - 1;
              while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
              {
                     i--;
              }
              s = s.substring(0, i+1);
       }
       return s;
}

//Trim(string):去除前后空格
function Trim(str)
{
       return RTrim(LTrim(str));
}

//去除所有空格function CutSpace(str)
{
    var whitespace = new String(" \t\n\r");
    var len = str.length;
    var i;
    var ch;
    var result = "";
    i = 0;
    while (i < len)
    {
        ch = str.charAt(i);        
        if (whitespace.indexOf(ch) == -1)
        {
           result += ch;
        }
        i++;
    }

    return result;
}