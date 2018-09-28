#!/usr/local/bin/python
# -*- coding: utf-8 -*-
import re
def trans1(ts,te,n):#(sprefql):#本查询语言的转化适用于所有 L的变量包含L1  L2的参数的情况。
    sprefql = '''Select  ?object Where {0}subject predicate[{1},{2}]:{3} object.{4}'''.format('{',ts,te,n,'}')

    '''Select  ? object
        Where{
        subject  ?p  object.
        ?p  rdf:type  rdft:property .
        ?p  rdft:hasTime  t^^xsd:date .
        ?p  rdft:hasNumUpdate  n .
}
'''
    pattern = re.compile(r'Select(.+|\s)Where\s{(.+|\s)}', re.S)
    result = re.match(pattern, sprefql)
    print('result',result)
    L=result.groups()[0].strip().split()
    print('L', L)
    Where_inner=result.groups()[1].strip().split(' ')
    print('Where_inner',Where_inner)
    pattern=re.compile(r'.*\[(.*)\].*',re.S)
    result_time= re.match(pattern, Where_inner[1])
    L_time = result_time.groups()[0].strip().split(',')
    print('L_time', L_time)
    S2S_Where=''
    if(L_time[0]==L_time[1]):
        S2S_Where=Where_inner[0]+' '+'?p'+' '+Where_inner[2]+"\n"\
         +'?p'+' '+'rdf:type'+' '+"rdft:property."+'\n'\
         +'?p  rdft:hasTime  {}^^xsd:date.'.format(L_time[0])+'\n'\
         +'?p  rdft:hasNumUpdate'+' '+Where_inner[1][-1]+'.'
    else:
        S2S_Where = Where_inner[0] + ' ' + '?p' + ' ' + Where_inner[2] + "\n" \
                    + '?p' + ' ' + 'rdf:type' + ' ' + "rdft:property." + '\n' \
                    + '?p  rdft:hasStartTime  {}^^xsd:date.'.format(L_time[0]) + '\n' \
                    +'?p  rdft:hasEndTime  {}^^xsd:date.'.format(L_time[1]) + '\n' \
                    + '?p  rdft:hasNumUpdate' + ' ' + Where_inner[1][-1] + '.'

    S2S_L=''
    for l in L:
        S2S_L=S2S_L+l+' '
    print('S2S_L',S2S_L)
    print('--------------------------------------------')
    S2S='Select '+S2S_L+'\n'+'WHERE {'+'\n'+S2S_Where+'}'
    print(S2S)
    # f = open('./date.txt', 'w')
    # f.write(S2S)
    # f.close
def trans2(c):
    Sparqlt='''Select ?count Where{?country rdft:haspopulation[?ts,?te]:?n ?count. Filter regex(?country,"china") Filter (?ts >= "2000-01-01"^^xsd:data && ?ts <= "2000-12-30"^^xsd:data)}'''
    pattern = re.compile(r'Select(.+|\s)Where\s*{(.+|\s)Filter(.+|\s|)Filter(.+|\s)}', re.S)
    result = re.match(pattern, Sparqlt)
    L = result.groups()[0].strip().split()
    print('L;',L)
    St2S_L = ''
    for l in L:
        St2S_L = St2S_L + l + ' '
    St2S_SPO=result.groups()[1].strip().split()
    print('St2S_SPO',St2S_SPO)
    St2S_fil=result.groups()[3].strip().split()
    print('St2S_fil',St2S_fil)
    St2S_filter = ''
    for f in St2S_fil:
        St2S_filter = St2S_filter + f + ' '
    print('-------------------------------------------------------------------')
    St2S='Select '+St2S_L+'\n'+'Where { \n'+St2S_SPO[0]+' ?'+str(St2S_SPO[1]).split('[')[0][str(St2S_SPO[1]).split('[')[0].find(':')+1:]+' '+St2S_SPO[2]+'\n'\
         +'?'+str(St2S_SPO[1]).split('[')[0][str(St2S_SPO[1]).split('[')[0].find(':')+1:]+' '+'rdf:type rdft:property .'+'\n'\
         +'?'+str(St2S_SPO[1]).split('[')[0][str(St2S_SPO[1]).split('[')[0].find(':')+1:]+' '+'rdft:hasStartTime ?ts .'+'\n'\
         +'?'+str(St2S_SPO[1]).split('[')[0][str(St2S_SPO[1]).split('[')[0].find(':')+1:]+' '+'rdft:hasEndTime ?te .'+'\n'\
         +'?'+str(St2S_SPO[1]).split('[')[0][str(St2S_SPO[1]).split('[')[0].find(':')+1:]+' '+'rdft:hasNumUpdate ?n .'+'\n'\
         +'Filter ('+St2S_SPO[0]+','+'"{0}"'.format(c)+')'+'\n'\
         +'Filter ('+St2S_filter+')'+'\n'+'}'
    print(St2S)

'''
        Filter regex(?country,"china")
        Filter (?ts >= '2000-01-01'^^<http://www.w3.org/2001/XMLSchema#date> && ?ts <= '2000-12-30'^^<http://www.w3.org/2001/XMLSchema#date>)

} '''


if __name__=='__main__':
    # sprefql = '''Select  ?object Where {subject hbvghbvgv[2001,2002]:3 object.}'''
    # trans1(2001,2001,3)
    trans2('china')