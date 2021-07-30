mysql -ppassword -uroot -P $1 <<EOF

use fpl;

select player, (xpabs-0.8687889144630985)/cost as c , team 
from calculatedplayerstatistics cps
where cps.position like "M%"
and cps.minutesplayed > 300
order by c desc
limit 20;

EOF
